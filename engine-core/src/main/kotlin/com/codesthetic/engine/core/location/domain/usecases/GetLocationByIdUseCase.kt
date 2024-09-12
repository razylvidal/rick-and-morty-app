package com.codesthetic.engine.core.location.domain.usecases

import com.codesthetic.engine.core.location.domain.Location
import com.codesthetic.engine.core.location.domain.LocationGateway
import com.codesthetic.engine.exception.NoSuchDataExistException
import javax.inject.Inject

/**
 * Created by razylvidal on July 02, 2024
 */
class GetLocationByIdUseCase
    @Inject
    constructor(
        private val gateway: LocationGateway,
    ) {
        operator fun invoke(params: Params): Location {
            return try {
                gateway.get(params.id)
            } catch (ex: NoSuchDataExistException) {
                Location()
            }
        }

        data class Params(val id: Int)
    }
