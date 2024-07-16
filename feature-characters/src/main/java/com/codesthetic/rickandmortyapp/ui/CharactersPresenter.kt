package com.codesthetic.rickandmortyapp.ui

import android.util.Log
import com.codesthetic.engine.core.characterdisplayconfig.domain.CharacterDisplayConfigHelper
import com.codesthetic.engine.core.characterdisplayconfig.domain.CharacterDisplayDataSource
import com.codesthetic.engine.core.characterdisplayconfig.domain.CharacterDisplayDataSource.Params
import com.codesthetic.engine.core.characterdisplayconfig.domain.CharacterDisplayDataSource.SortByName
import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.characters.domain.usecases.FetchCharactersUseCase
import com.codesthetic.engine.core.characters.domain.usecases.LoadCharactersUseCase
import com.codesthetic.engine.core.characters.domain.usecases.SearchCharacterUseCase
import com.codesthetic.engine.core.gender.domain.GetGenderUseCase
import com.codesthetic.engine.core.status.domain.GetStatusUseCase
import com.codesthetic.engine.exception.NoSuchDataExistException
import com.codesthetic.utilsandroid.capitalizeFirstChar
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by razylvidal on February 05, 2024
 */
class CharactersPresenter
    @Inject
    constructor(
        private val loadCharactersUseCase: LoadCharactersUseCase,
        private val fetchCharactersUseCase: FetchCharactersUseCase,
        private val characterDisplayConfigHelper: CharacterDisplayConfigHelper,
        private val getGenderUseCase: GetGenderUseCase,
        private val getStatusUseCase: GetStatusUseCase,
        private val searchCharacterUseCase: SearchCharacterUseCase,
    ) : CharactersContract.Presenter {
        private var view: CharactersContract.View? = null

        private val scope = MainScope()

        private var currentPage: Int = 0

        private var characters: List<Character> = emptyList()

        private var loadMoreJob: Job? = null

        private var searchJob: Job? = null

        private var gender: List<String> = emptyList()

        private var status: List<String> = emptyList()

        private var searchQuery: String = ""

        private var searchResult: List<Character> = emptyList()

        private var allItemsLoaded: Boolean = false

        override fun onViewReady(view: CharactersContract.View) {
            this.view = view
            setup()
        }

        private fun setup() {
            view?.renderLoading(true)
            scope.launch {
                characters = loadCharactersUseCase.load()
                loadFilters()
                view?.showCharacters(characters, noMoreToLoad = false, isRestoringDefaults = false)
                view?.renderEmptyState(isVisible = false)
                view?.renderLoading(false)
            }
        }

        private fun loadFilters() {
            gender = getGenderUseCase().get()
            status = getStatusUseCase.invoke().get()
        }

        override fun onDestroy() {
            this.view = null
        }

        override fun onSearchValueChanged(query: String) {
            searchJob?.cancel()
            if (query.isNotEmpty() && query.length > 1) {
                searchQuery = query
                searchJob =
                    scope.launch {
                        view?.renderLoading(true)
                        try {
                            delay(SEARCH_DELAY)
                            searchResult =
                                searchCharacterUseCase.invoke(
                                    SearchCharacterUseCase.Param(query)
                                ).searchResult
                            filterCharacters(searchResult)
                        } catch (ex: NoSuchDataExistException) {
                            searchResult = emptyList()
                            view?.renderEmptyState(ex.message, true)
                            view?.renderLoading(false)
                        }
                    }
            } else {
                searchResult = emptyList()
                searchQuery = ""
                filterCharacters(characters)
            }
        }

        private fun filterCharacters(characters: List<Character>) {
            val displayConfig = characterDisplayConfigHelper.getDisplayConfig()

            if (!displayConfig.hasActiveFilters() && searchQuery.isEmpty()) {
                val sortedCharacters = sortCharacters(characters, displayConfig.sortBy)
                view?.renderEmptyState()
                view?.showCharacters(sortedCharacters, allItemsLoaded, isRestoringDefaults = true)
                return
            }

            val filteredByGender =
                characters.takeIf {
                    displayConfig.isDefault(Params.Gender)
                } ?: characters.filter { it.gender.capitalizeFirstChar().contentEquals(displayConfig.gender, true) }

            val filterByStatus =
                if (filteredByGender.isNotEmpty()) {
                    filteredByGender.takeIf { displayConfig.isDefault(Params.Status) }
                        ?: filteredByGender.filter {
                            it.status.capitalizeFirstChar().contentEquals(displayConfig.status, true)
                        }
                } else {
                    emptyList()
                }

            val sortedCharacters = sortCharacters(filterByStatus, displayConfig.sortBy)
            view?.renderLoading(false)
            view?.renderEmptyState(isVisible = sortedCharacters.isEmpty())
            view?.showFilteredCharacters(sortedCharacters)
        }

        private fun sortCharacters(
            characters: List<Character>,
            sortBy: SortByName,
        ): List<Character> {
            if (characters.isEmpty()) return characters

            return when (sortBy) {
                SortByName.DEFAULT -> characters
                SortByName.ASCENDING -> characters.sortedBy { it.name }
                SortByName.DESCENDING -> characters.sortedByDescending { it.name }
            }
        }

        override fun onFilterButtonClicked() {
            view?.showFilter(gender, status)
        }

        override fun onApplyFilters(filter: CharacterDisplayDataSource) {
            characterDisplayConfigHelper.save(filter)
            filterCharacters(
                characters.takeIf { searchQuery.isBlank() } ?: searchResult
            )
        }

        override fun onResetFilters() {
            characterDisplayConfigHelper.save(CharacterDisplayDataSource())
            filterCharacters(characters)
        }

        override fun onCharacterClicked(id: Int) {
            view?.navigateToCharacter(id)
        }

        override fun onLoadMore(currentItemSize: Int) {
            loadMoreJob?.cancel()
            loadMoreJob =
                scope.launch {
                    try {
                        Log.e(">>", "Loading more page $currentItemSize")
                        currentPage = currentItemSize / PAGE_SIZE_LIMIT
                        val newSetOfCharacters = fetchCharactersUseCase.fetch(currentPage.inc())
                        characters += newSetOfCharacters
                        allItemsLoaded = newSetOfCharacters.size < PAGE_SIZE_LIMIT
                        loadFilters()
                        view?.showCharacters(characters, allItemsLoaded, isRestoringDefaults = false)
                    } catch (ex: Exception) {
                        Log.e("onLoadMore failed", "${ex.cause} == ${ex.message}")
                    }
                }
        }

        override fun onSortButtonClicked() {
            val curConfig = characterDisplayConfigHelper.getDisplayConfig()

            val newConfig =
                when (curConfig.sortBy) {
                    SortByName.DEFAULT -> {
                        SortByName.ASCENDING
                    }

                    SortByName.ASCENDING -> {
                        SortByName.DESCENDING
                    }

                    SortByName.DESCENDING -> {
                        SortByName.DEFAULT
                    }
                }

            characterDisplayConfigHelper.save(curConfig.copy(sortBy = newConfig))
            if (searchQuery.isEmpty()) {
                Log.e(">>", "sort without query")
                filterCharacters(characters)
            } else {
                Log.e(">>", "sort with query")
                filterCharacters(searchResult)
            }

            view?.updateSortByNameButton(newConfig)
        }

        companion object {
            private const val PAGE_SIZE_LIMIT = 20
            private const val SEARCH_DELAY = 300L
        }
    }
