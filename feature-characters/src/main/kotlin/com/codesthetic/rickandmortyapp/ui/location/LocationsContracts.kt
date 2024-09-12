package com.codesthetic.rickandmortyapp.ui.location

import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.location.domain.Location

/**
 * Created by razylvidal on February 05, 2024
 */
interface LocationsContracts {
    interface View {
        fun renderLoading(isVisible: Boolean)

        fun showLocationDetails(location: Location)

        fun showResidents(residents: List<Character>)
    }

    interface Presenter {
        fun onViewReady(
            view: View,
            locationId: Int,
        )

        fun onDestroy()
    }
}
