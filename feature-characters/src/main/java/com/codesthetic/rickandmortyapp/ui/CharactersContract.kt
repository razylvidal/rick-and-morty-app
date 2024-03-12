package com.codesthetic.rickandmortyapp.ui

/**
 * Created by razylvidal on February 05, 2024
 */
interface CharactersContract {

    interface View {

    }

    interface Presenter {
        fun onViewReady(view : View)

        fun onDestroy()
    }
}
