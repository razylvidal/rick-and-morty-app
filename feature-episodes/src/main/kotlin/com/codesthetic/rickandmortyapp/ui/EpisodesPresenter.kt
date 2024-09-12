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

        override fun onViewReady(view: EpisodesContracts.View) {
            this.view = view
            renderEpisodes()
        }

        private fun renderEpisodes(season: Int = 1) {
            val episodes = getEpisodesBySeason.invoke(GetEpisodesBySeason.Param(season))
            view?.renderNewSeason("Season $season")
            view?.showEpisodes(episodes)
        }

        override fun onDestroy() {
            this.view = null
        }

        override fun onEpisodeClicked(episodeId: Int) {
            // TODO("Not yet implemented")
        }

        override fun onSeasonClicked() {
            // TODO("Not yet implemented")
        }

        override fun onUpdateSeason() {
            //  TODO("Not yet implemented")
        }
    }
