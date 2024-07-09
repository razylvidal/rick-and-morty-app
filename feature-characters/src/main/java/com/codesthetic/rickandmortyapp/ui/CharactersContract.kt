package com.codesthetic.rickandmortyapp.ui

import com.codesthetic.engine.core.characters.domain.Character

/**
 * Created by razylvidal on February 05, 2024
 */
interface CharactersContract {
    interface View {
        fun showLoading()

        fun hideLoading()

        fun showFilter(
            gender: List<String>,
            status: List<String>,
        )

        fun hideFilter()

        fun showCharacters(
            characters: List<Character>,
            allItemsLoaded: Boolean,
        )

        fun showEmptyState()

        fun navigateToCharacter(id: Int)
    }

    interface Presenter {
        fun onViewReady(view: View)

        fun onDestroy()

        fun onUpdateCharacters(characters: List<Character>)

        fun onSearchCharacter(query: String)

        fun onFilterButtonClicked()

        fun onApplyFilters()

        fun onCharacterClicked(id: Int)

        fun onLoadMore(currentItemSize: Int)
    }
}
