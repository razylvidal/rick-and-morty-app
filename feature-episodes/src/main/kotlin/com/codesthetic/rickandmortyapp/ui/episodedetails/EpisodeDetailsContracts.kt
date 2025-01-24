package com.codesthetic.rickandmortyapp.ui.episodedetails

import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.episodes.domain.Episode

/**
 * Created by razylvidal on 1/24/25.
 */
interface EpisodeDetailsContracts {

    interface View {
        fun showEpisodeDetails(episode : Episode)

        fun showEpisodeCharacters(characters: List<Character>)
    }

    interface Presenter {
        fun onViewReady(view: View)

        fun onDestroy()
    }
}
