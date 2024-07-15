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
                view?.showCharacters(characters, false)
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
                            configureCharacterDisplay(searchResult, true)
                        } catch (ex: NoSuchDataExistException) {
                            searchResult = emptyList()
                            view?.renderEmptyState(ex.message, true)
                            view?.renderLoading(false)
                        }
                    }
            } else {
                searchResult = emptyList()
                searchQuery = ""
                configureCharacterDisplay(characters, false)
            }
        }

        private fun configureCharacterDisplay(
            characters: List<Character>,
            isFiltering: Boolean,
        ) {
            val displayConfig = characterDisplayConfigHelper.getDisplayConfig()

            if (!displayConfig.hasActiveFilters() && searchQuery.isEmpty()) {
                view?.renderEmptyState()
                view?.showCharacters(characters, allItemsLoaded, false)
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

            val charactersToDisplay =
                if (filterByStatus.isNotEmpty()) {
                    when (displayConfig.sortBy) {
                        SortByName.DEFAULT -> filterByStatus
                        SortByName.ASCENDING -> filterByStatus.sortedBy { it.name }
                        SortByName.DESCENDING -> filterByStatus.sortedByDescending { it.name }
                    }
                } else {
                    filterByStatus
                }
            view?.renderLoading(false)
            view?.renderEmptyState(isVisible = charactersToDisplay.isEmpty())
            view?.showCharacters(charactersToDisplay, allItemsLoaded, isFiltering)
        }

        override fun onFilterButtonClicked() {
            view?.showFilter(gender, status)
        }

        override fun onApplyFilters(filter: CharacterDisplayDataSource) {
            characterDisplayConfigHelper.save(filter)
            configureCharacterDisplay(
                characters.takeIf { searchQuery.isBlank() } ?: searchResult,
                true
            )
        }

        override fun onResetFilters() {
            characterDisplayConfigHelper.save(CharacterDisplayDataSource())
            configureCharacterDisplay(characters, false)
        }

        override fun onCharacterClicked(id: Int) {
            view?.navigateToCharacter(id)
        }

        override fun onLoadMore(currentItemSize: Int) {
            loadMoreJob?.cancel()
            loadMoreJob =
                scope.launch {
                    try {
                        currentPage = currentItemSize / PAGE_SIZE_LIMIT
                        val newSetOfCharacters = fetchCharactersUseCase.fetch(currentPage.inc())
                        characters += newSetOfCharacters
                        allItemsLoaded = newSetOfCharacters.size < PAGE_SIZE_LIMIT
                        loadFilters()
                        view?.showCharacters(characters, allItemsLoaded)
                        Log.e("onLoadMore", "character size ${characters.size}")
                        Log.e("onLoadMore", "allItemsLoaded $allItemsLoaded")
                    } catch (ex: Exception) {
                        Log.e("onLoadMore failed", "${ex.cause} == ${ex.message}")
                    }
                }
        }

        companion object {
            private const val PAGE_SIZE_LIMIT = 20
            private const val SEARCH_DELAY = 300L
        }
    }
