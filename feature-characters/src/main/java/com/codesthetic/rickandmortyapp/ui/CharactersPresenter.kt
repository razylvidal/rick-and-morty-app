package com.codesthetic.rickandmortyapp.ui

/**
 * Created by razylvidal on February 05, 2024
 */
class CharactersPresenter : CharactersContract.Presenter {

    private var view : CharactersContract.View? = null

    override fun onViewReady(view: CharactersContract.View) {
        this.view = view
    }

    override fun onDestroy() {
        this.view = null
    }


}
