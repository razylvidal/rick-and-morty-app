@file:Suppress("MagicNumber")

package com.codesthetic.rickandmortyapp.ui

import com.codesthetic.engine.core.characters.domain.usecases.GetCharactersUseCase
import com.codesthetic.engine.core.episodes.domain.usecases.GetEpisodeUseCase
import com.codesthetic.engine.core.location.domain.usecases.GetLocationUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by razylvidal on March 15, 2024
 */
class SplashPresenter
    @Inject
    constructor(
        private val getCharactersUseCase: GetCharactersUseCase,
        private val getEpisodeUseCase: GetEpisodeUseCase,
        private val getLocationUseCase: GetLocationUseCase,
    ) : SplashContract.Presenter {
        private var view: SplashContract.View? = null

        private val scope = MainScope()

        override fun onViewReady(view: SplashContract.View) {
            this.view = view
            initializeData()
        }

        private fun initializeData() {
            view?.showLoadingBar()
            view?.updateProgressIndicator(10)
            scope.launch {
                try {
                    delay(1000L)
                    getCharactersUseCase.get()
                    view?.updateProgressIndicator(40)
                    getEpisodeUseCase.get()
                    view?.updateProgressIndicator(70)
                    getLocationUseCase.get()
                    view?.updateProgressIndicator(100)
                    view?.navigateToMainActivity()
                } catch (exception: Exception) {
                    view?.showToast("${exception.message}")
                }
            }
        }

        override fun onDestroy() {
            this.view = null
        }
    }
