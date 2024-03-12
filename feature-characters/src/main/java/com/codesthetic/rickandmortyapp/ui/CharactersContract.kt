package com.codesthetic.rickandmortyapp.ui

import com.codesthetic.engine.core.characters.domain.Character

/**
 * Created by razylvidal on February 05, 2024
 */
interface CharactersContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showFilter()
        fun hideFilter()
        fun showCharacters()
        fun showEmptyState()
        fun navigateToCharacter()
    }

    interface Presenter {
        fun onViewReady(view : View)
        fun onDestroy()
        fun onUpdateCharacters(characters : List<Character>)
        fun onSearchCharacter(query : String)
        fun onFilterButtonClicked()
        fun onApplyFilters()
        fun onCharacterClicked()
    }
}
