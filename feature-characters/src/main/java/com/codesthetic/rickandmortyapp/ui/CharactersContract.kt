package com.codesthetic.rickandmortyapp.ui

/**
 * Created by razylvidal on February 05, 2024
 */
interface CharactersContract {

    interface View {
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun onViewReady(view : View)

        fun onDestroy()
    }
}
