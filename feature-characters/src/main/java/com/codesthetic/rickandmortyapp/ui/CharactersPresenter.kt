package com.codesthetic.rickandmortyapp.ui

import android.util.Log
import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.characters.domain.usecases.FetchCharactersUseCase
import com.codesthetic.engine.core.characters.domain.usecases.LoadCharactersUseCase
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
    ) : CharactersContract.Presenter {
        private var view: CharactersContract.View? = null
        private val scope = MainScope()
        private var currentPage: Int = 0
        private var characters: List<Character> = emptyList()
        private var loadMoreJob: Job? = null

        override fun onViewReady(view: CharactersContract.View) {
            this.view = view
            setup()
        }

        private fun setup() {
            scope.launch {
                val characters = loadCharactersUseCase.load()
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
            TODO("Not yet implemented")
        }

        override fun onApplyFilters() {
            TODO("Not yet implemented")
        }

        override fun onCharacterClicked(id: Int) {
            TODO("Not yet implemented")
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
