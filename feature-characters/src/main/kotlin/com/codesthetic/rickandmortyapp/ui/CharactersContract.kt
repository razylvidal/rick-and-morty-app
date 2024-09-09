package com.codesthetic.rickandmortyapp.ui

import com.codesthetic.engine.core.characterdisplayconfig.domain.CharacterDisplayDataSource
import com.codesthetic.engine.core.characters.domain.Character

/**
 * Created by razylvidal on February 05, 2024
 */
interface CharactersContract {
    interface View {
        fun renderLoading(isVisible: Boolean)

        fun showFilter(
            gender: List<String>,
            status: List<String>,
        )

        fun showCharacters(
            characters: List<Character>,
            noMoreToLoad: Boolean,
            isRestoringDefaults: Boolean,
        )

        fun showFilteredCharacters(characters: List<Character>)

        fun renderEmptyState(
            message: String = "",
            isVisible: Boolean = false,
        )

        fun navigateToCharacter(id: Int)

        fun updateSortByNameButton(selected: CharacterDisplayDataSource.SortByName)
    }

    interface Presenter {
        fun onViewReady(view: View)

        fun onDestroy()

        fun onSearchValueChanged(query: String)

        fun onFilterButtonClicked()

        fun onApplyFilters(filter: CharacterDisplayDataSource)

        fun onResetFilters()

        fun onCharacterClicked(id: Int)

        fun onLoadMore(currentItemSize: Int)

        fun onSortButtonClicked()
    }
}
