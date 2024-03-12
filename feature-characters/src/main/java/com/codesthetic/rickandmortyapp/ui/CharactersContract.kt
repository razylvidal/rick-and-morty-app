package com.codesthetic.rickandmortyapp.ui

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
    }

    interface Presenter {
        fun onViewReady(view : View)

        fun onDestroy()
    }
}
