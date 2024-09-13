package com.codesthetic.rickandmortyapp.ui

import com.codesthetic.engine.core.episodes.domain.usecases.GetEpisodesBySeason
import com.codesthetic.engine.core.episodes.domain.usecases.GetSeasonsUseCase
import javax.inject.Inject

/**
 * Created by razylvidal on February 05, 2024
 */
class EpisodesPresenter
    @Inject
    constructor(
        private val getEpisodesBySeason: GetEpisodesBySeason,
        private val getSeasonsUseCase: GetSeasonsUseCase,
    ) : EpisodesContracts.Presenter {
        private var view: EpisodesContracts.View? = null

        private var selectedSeason = 1

        override fun onViewReady(view: EpisodesContracts.View) {
            this.view = view
            renderEpisodes()
        }

        private fun renderEpisodes() {
            val episodes = getEpisodesBySeason.invoke(GetEpisodesBySeason.Param(selectedSeason))
            view?.renderSeason("Season $selectedSeason")
            view?.showEpisodes(episodes)
        }

        override fun onDestroy() {
            this.view = null
        }

        override fun onEpisodeClicked(episodeId: Int) {
            // TODO("Not yet implemented")
        }

        override fun onSeasonClicked() {
            view?.showSeasonDialog(
                currentSeason = selectedSeason,
                getSeasonsUseCase.invoke()
            )
        }

        override fun onUpdateSeason(season: Int) {
            selectedSeason = season
            renderEpisodes()
        }
    }
