package com.codesthetic.engine.core.location.domain.usecases

import com.codesthetic.engine.core.location.domain.Location
import com.codesthetic.engine.core.location.domain.LocationGateway
import javax.inject.Inject

/**
 * Created by razylvidal on February 05, 2024
 */
class GetLocationUseCase @Inject constructor(
    private val gateway: LocationGateway
) {

    suspend fun get(): List<Location> {
        return gateway.get()
    }

    suspend fun get(id: Int): Location {
        return gateway.get(id)
    }
}
