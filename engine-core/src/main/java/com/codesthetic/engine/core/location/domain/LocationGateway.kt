package com.codesthetic.engine.core.location.domain

/**
 * Created by razylvidal on December 21, 2023
 */
interface LocationGateway {

    suspend fun fetch(): List<Location>

    suspend fun get(): List<Location>

    suspend fun get(id: Int): Location

    suspend fun save(locations: List<Location>)

    fun clear()
}
