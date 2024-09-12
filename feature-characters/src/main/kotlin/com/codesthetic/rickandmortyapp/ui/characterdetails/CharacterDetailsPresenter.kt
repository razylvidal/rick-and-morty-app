package com.codesthetic.rickandmortyapp.ui.characterdetails

import android.util.Log
import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.characters.domain.usecases.GetCharacterByIDUseCase
import com.codesthetic.engine.core.episodes.domain.Episode
import com.codesthetic.engine.core.episodes.domain.usecases.GetEpisodeByIdUseCase
import com.codesthetic.engine.core.location.domain.usecases.GetLocationByIdUseCase
import com.codesthetic.engine.exception.NoSuchDataExistException
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
        private var view: CharacterDetailsContract.View? = null
        private val scope = MainScope()

        private var character: Character? = null

        override fun onViewReady(
            view: CharacterDetailsContract.View,
            id: Int,
        ) {
            this.view = view
            setupCharacterDetails(id)
        }

        private fun setupCharacterDetails(id: Int) {
            view?.showLoading()
            scope.launch {
                try {
                    character = getCharacterByIDUseCase.get(id)
                    character?.let {
                        val originName = getLocationByIDUseCase.invoke(GetLocationByIdUseCase.Params(it.origin)).name
                        val locationName =
                            getLocationByIDUseCase.invoke(GetLocationByIdUseCase.Params(it.location)).name
                        Log.e(">>", "${character?.location}")
                        val episodes = getEpisodes(it.episode)
                        view?.showCharacterDetails(it, originName, locationName, episodes)
                    }
                    view?.hideLoading()
                } catch (ex: NoSuchDataExistException) {
                    view?.showToast(ex.message)
                }
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

        override fun onLocationClicked() {
            view?.showLocationBottomSheetDialog(character!!.location)
        }

        override fun onOriginClicked() {
            view?.showLocationBottomSheetDialog(character!!.origin)
        }

        override fun onUpdateCharacter(characterId: Int) {
            setupCharacterDetails(characterId)
        }
    }
