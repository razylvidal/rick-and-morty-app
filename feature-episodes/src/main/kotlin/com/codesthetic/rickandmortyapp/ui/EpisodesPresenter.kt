package com.codesthetic.rickandmortyapp.ui

import com.codesthetic.engine.core.episodes.domain.usecases.GetEpisodesBySeason
import javax.inject.Inject

/**
 * Created by razylvidal on February 05, 2024
 */
class EpisodesPresenter
    @Inject
    constructor(
        private val getEpisodesBySeason: GetEpisodesBySeason,
    ) : EpisodesContracts.Presenter {
        private var view: EpisodesContracts.View? = null

        private var currentSeason = 1

        override fun onViewReady(view: EpisodesContracts.View) {
            this.view = view
            renderEpisodes()
        }

        private fun renderEpisodes() {
            val episodes = getEpisodesBySeason.invoke(GetEpisodesBySeason.Param(currentSeason))
            view?.renderSeason("Season $currentSeason")
            view?.showEpisodes(episodes)
        }

        override fun onDestroy() {
            this.view = null
        }

        override fun onEpisodeClicked(episodeId: Int) {
            // TODO("Not yet implemented")
        }

        override fun onSeasonClicked() {
            val seasons = listOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9)
            view?.showSeasonDialog(
                currentSeason = currentSeason,
                seasons
            )
        }

        override fun onUpdateSeason() {
            //  TODO("Not yet implemented")
        }
    }
