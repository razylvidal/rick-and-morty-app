package com.codesthetic.rickandmortyapp.ui

import com.codesthetic.engine.core.episodes.domain.Episode

/**
 * Created by razylvidal on February 05, 2024
 */
interface EpisodesContracts {
    interface View {
        fun showEpisodes(episodes: List<Episode>)

        fun renderSeason(season: String)

        fun showSeasonDialog(
            currentSeason: Int,
            seasons: List<Int>,
        )
    }

    interface Presenter {
        fun onViewReady(view: View)

        fun onDestroy()

        fun onEpisodeClicked(episodeId: Int)

        fun onSeasonClicked()

        fun onUpdateSeason()
    }
}
