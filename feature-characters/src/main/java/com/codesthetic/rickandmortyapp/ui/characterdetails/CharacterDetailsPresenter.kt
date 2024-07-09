package com.codesthetic.rickandmortyapp.ui.characterdetails

import android.util.Log
import com.codesthetic.engine.core.characters.domain.usecases.GetCharacterByIDUseCase
import com.codesthetic.engine.core.episodes.domain.Episode
import com.codesthetic.engine.core.episodes.domain.usecases.GetEpisodeByIdUseCase
import com.codesthetic.engine.core.location.domain.usecases.GetLocationByIdUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by razylvidal on July 01, 2024
 */
class CharacterDetailsPresenter
    @Inject
    constructor(
        private val getCharacterByIDUseCase: GetCharacterByIDUseCase,
        private val getLocationByIDUseCase: GetLocationByIdUseCase,
        private val getEpisodeByIdUseCase: GetEpisodeByIdUseCase,
    ) : CharacterDetailsContract.Presenter {
        private var characterId: Int = 0
        private var view: CharacterDetailsContract.View? = null
        private val scope = MainScope()

        override fun onViewReady(
            view: CharacterDetailsContract.View,
            id: Int,
        ) {
            this.view = view
            characterId = id
            setup()
        }

        private fun setup() {
            view?.showLoading()
            scope.launch {
                val character = getCharacterByIDUseCase.get(characterId)
                val originName = getLocationByIDUseCase.invoke(GetLocationByIdUseCase.Params(character.origin)).name
                val locationName = getLocationByIDUseCase.invoke(GetLocationByIdUseCase.Params(character.location)).name
                Log.e(">>", "${character.location}")
                val episodes = getEpisodes(character.episode)
                view?.showCharacterDetails(character, originName, locationName, episodes)
                view?.hideLoading()
            }
        }

        private suspend fun getEpisodes(ids: List<Int>): List<Episode> {
            val episodes: MutableList<Episode> = mutableListOf()
            ids.forEach {
                episodes.add(getEpisodeByIdUseCase.invoke(GetEpisodeByIdUseCase.Param(it)))
            }
            return episodes.toList()
        }

        override fun onDestroy() {
            this.view = null
        }
    }
