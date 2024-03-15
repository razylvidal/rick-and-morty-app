package com.codesthetic.rickandmortyapp.ui

import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.characters.domain.usecases.GetCharactersUseCase
import javax.inject.Inject

/**
 * Created by razylvidal on February 05, 2024
 */
class CharactersPresenter
    @Inject
    constructor(
        private val getCharactersUseCase: GetCharactersUseCase,
    ) : CharactersContract.Presenter {
        private var view: CharactersContract.View? = null

        override fun onViewReady(view: CharactersContract.View) {
            this.view = view
        }

        override fun onDestroy() {
            this.view = null
        }

        private fun setUpCharacters()  {
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
    }
