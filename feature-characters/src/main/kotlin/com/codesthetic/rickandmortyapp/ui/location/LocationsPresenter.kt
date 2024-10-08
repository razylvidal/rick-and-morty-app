package com.codesthetic.rickandmortyapp.ui.location

import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.characters.domain.usecases.GetCharacterByIDUseCase
import com.codesthetic.engine.core.location.domain.usecases.GetLocationByIdUseCase
import javax.inject.Inject

/**
 * Created by razylvidal on February 05, 2024
 */
class LocationsPresenter
    @Inject
    constructor(
        private val getCharacterByIDUseCase: GetCharacterByIDUseCase,
        private val getLocationByIdUseCase: GetLocationByIdUseCase,
    ) : LocationsContracts.Presenter {
        private var view: LocationsContracts.View? = null

        private var residents: MutableList<Character> = mutableListOf()

        override fun onViewReady(
            view: LocationsContracts.View,
            locationId: Int,
        ) {
            this.view = view
            setup(locationId)
        }

        private fun setup(locationId: Int) {
            val location = getLocationByIdUseCase.invoke(GetLocationByIdUseCase.Params(locationId))
            location.residents.forEach { id ->
                runCatching {
                    residents.add(getCharacterByIDUseCase.get(id))
                }
            }
            view?.showLocationDetails(location)
            view?.showResidents(residents)
        }

        override fun onDestroy() {
            this.view = null
        }
    }
