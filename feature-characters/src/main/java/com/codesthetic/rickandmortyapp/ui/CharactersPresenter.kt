package com.codesthetic.rickandmortyapp.ui

import com.codesthetic.engine.core.characters.domain.Character
import javax.inject.Inject

/**
 * Created by razylvidal on February 05, 2024
 */
class CharactersPresenter
    @Inject
    constructor() : CharactersContract.Presenter {
        private var view: CharactersContract.View? = null

        override fun onViewReady(view: CharactersContract.View) {
            this.view = view
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

        override fun onCharacterClicked() {
            TODO("Not yet implemented")
        }
    }
