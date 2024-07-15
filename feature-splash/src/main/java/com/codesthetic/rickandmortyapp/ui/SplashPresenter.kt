@file:Suppress("MagicNumber")

package com.codesthetic.rickandmortyapp.ui

import android.util.Log
import com.codesthetic.engine.core.characters.domain.usecases.LoadCharactersUseCase
import com.codesthetic.engine.core.episodes.domain.usecases.LoadEpisodeUseCase
import com.codesthetic.engine.core.location.domain.usecases.LoadLocationUseCase
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
        private val loadCharactersUseCase: LoadCharactersUseCase,
        private val loadEpisodeUseCase: LoadEpisodeUseCase,
        private val loadLocationUseCase: LoadLocationUseCase,
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
                    loadCharactersUseCase.load()
                    Log.e(">>", "loadCharactersUseCase done")
                    view?.updateProgressIndicator(40)
                    loadEpisodeUseCase.get()
                    Log.e(">>", "loadEpisodeUseCase done")
                    view?.updateProgressIndicator(70)
                    loadLocationUseCase.get()
                    Log.e(">>", "loadLocationUseCase done")
                    view?.updateProgressIndicator(100)
                    view?.navigateToMainActivity()
                } catch (exception: Exception) {
                    view?.showToast("${exception.message}")
                    Log.e("error", "${exception.message}, ${exception.cause}")
                }
            }
        }

        override fun onDestroy() {
            this.view = null
        }
    }
