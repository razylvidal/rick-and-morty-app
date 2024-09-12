package com.codesthetic.engine.core.location.domain

/**
 * Created by razylvidal on December 21, 2023
 */
interface LocationGateway {
    suspend fun fetch(): List<Location>

    suspend fun get(): List<Location>

    fun get(id: Int): Location

    fun save(locations: Location)

    fun clear()
}
