package com.codesthetic.engine.core.location.domain.usecases

import com.codesthetic.engine.core.location.domain.Location
import com.codesthetic.engine.core.location.domain.LocationGateway
import javax.inject.Inject

/**
 * Created by razylvidal on December 27, 2023
 */
class FetchLocationUseCase
    @Inject
    constructor(
        private val gateway: LocationGateway,
    ) {
        suspend fun fetch(): List<Location> {
            val locations = listOf(Location()) + gateway.fetch()
            locations.map {
                gateway.save(it)
            }
            return locations
        }
    }
