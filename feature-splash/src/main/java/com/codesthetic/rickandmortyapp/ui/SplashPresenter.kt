package com.codesthetic.rickandmortyapp.ui

import com.codesthetic.engine.core.characters.domain.usecases.FetchCharactersUseCase
import com.codesthetic.engine.core.episodes.domain.usecases.FetchEpisodesUseCase
import com.codesthetic.engine.core.location.domain.usecases.FetchLocationUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

/**
 * Created by razylvidal on March 15, 2024
 */
class SplashPresenter
    @Inject
    constructor(
        private val fetchCharactersUseCase: FetchCharactersUseCase,
        private val fetchEpisodesUseCase: FetchEpisodesUseCase,
        private val fetchLocationUseCase: FetchLocationUseCase,
    ) : SplashContract.Presenter {
        private var view: SplashContract.View? = null

        private val scope = MainScope()

        override fun onViewReady(view: SplashContract.View) {
            this.view = view
            initializeData()
        }

        private fun initializeData() {
            view?.updateProgressIndicator(10)
            scope.launch {
                try {
                    fetchCharactersUseCase.fetch()
                    view?.updateProgressIndicator(40)
                    fetchEpisodesUseCase.fetch()
                    view?.updateProgressIndicator(70)
                    fetchLocationUseCase.fetch()
                    view?.updateProgressIndicator(100)
                    delay(1000L)
                    view?.showToast("Done!")
                } catch (exception: Exception) {
                    when (exception) {
                        is ConnectException -> {
                            view?.showToast("${exception.message}")
                        }

                        else -> {
                            view?.showToast("${exception.message}")
                        }
                    }
                }
            }
        }

        override fun onDestroy() {
            this.view = null
        }
    }
