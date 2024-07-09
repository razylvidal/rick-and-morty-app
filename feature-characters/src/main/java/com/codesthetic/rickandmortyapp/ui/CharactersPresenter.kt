package com.codesthetic.rickandmortyapp.ui

import android.util.Log
import com.codesthetic.engine.core.characterdisplayconfig.domain.CharacterDisplayConfigHelper
import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.characters.domain.usecases.FetchCharactersUseCase
import com.codesthetic.engine.core.characters.domain.usecases.LoadCharactersUseCase
import com.codesthetic.engine.core.gender.domain.GetGenderUseCase
import com.codesthetic.engine.core.status.domain.GetStatusUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
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
    ) : CharactersContract.Presenter {
        private var view: CharactersContract.View? = null
        private val scope = MainScope()
        private var currentPage: Int = 0
        private var characters: List<Character> = emptyList()
        private var loadMoreJob: Job? = null
        private var gender: List<String> = emptyList()
        private var status: List<String> = emptyList()

        override fun onViewReady(view: CharactersContract.View) {
            this.view = view
            setup()
        }

        private fun setup() {
            scope.launch {
                val characters = loadCharactersUseCase.load()
                gender = getGenderUseCase.invoke().get()
                status = getStatusUseCase.invoke().get()
                view?.showCharacters(characters, false)
            }
        }

        override fun onDestroy() {
            this.view = null
        }

        override fun onUpdateCharacters(characters: List<Character>) {
            TODO("Not yet implemented")
        }

        override fun onSearchCharacter(query: String) {
            TODO("Not yet implemented")
        }

        override fun onFilterButtonClicked() {
            view?.showFilter(gender, status)
        }

        override fun onApplyFilters() {
            TODO("Not yet implemented")
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
                        val hasLessThanTheExpectedSize = newSetOfCharacters.size < PAGE_SIZE_LIMIT
                        view?.showCharacters(characters + newSetOfCharacters, hasLessThanTheExpectedSize)
                    } catch (ex: Exception) {
                        Log.e("onLoadMore failed", "${ex.cause} == ${ex.message}")
                    }
                }
        }

        companion object {
            private const val PAGE_SIZE_LIMIT = 20
        }
    }
