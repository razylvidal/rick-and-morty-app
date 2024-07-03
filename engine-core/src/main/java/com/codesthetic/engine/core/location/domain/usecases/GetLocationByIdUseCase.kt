package com.codesthetic.engine.core.location.domain.usecases

import com.codesthetic.engine.core.location.domain.Location
import com.codesthetic.engine.core.location.domain.LocationGateway
import javax.inject.Inject

/**
 * Created by razylvidal on July 02, 2024
 */
class GetLocationByIdUseCase
    @Inject
    constructor(
        private val gateway: LocationGateway,
    ) {
        suspend operator fun invoke(params: Params): Location {
            return gateway.get(params.id)
        }

        data class Params(val id: Int)
    }
