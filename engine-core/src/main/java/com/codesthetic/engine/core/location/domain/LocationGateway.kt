package com.codesthetic.engine.core.location.domain

/**
 * Created by razylvidal on December 21, 2023
 */
interface LocationGateway {

    suspend fun get(): Location
}
