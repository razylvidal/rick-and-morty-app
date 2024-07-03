package com.codesthetic.rickandmortyapp.ui.characterdetails

import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.episodes.domain.Episode

/**
 * Created by razylvidal on July 01, 2024
 */
interface CharacterDetailsContract {
    interface View {
        fun showCharacterDetails(
            character: Character,
            locationName: String,
            episodes: List<Episode>,
        )

        fun showLoading()

        fun hideLoading()
    }

    interface Presenter {
        fun onViewReady(
            view: View,
            id: Int,
        )

        fun onDestroy()
    }
}
