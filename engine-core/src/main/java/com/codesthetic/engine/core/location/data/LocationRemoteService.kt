package com.codesthetic.engine.core.location.data

import retrofit2.http.GET

/**
 * Created by razylvidal on December 21, 2023
 */
interface LocationRemoteService {
    @GET("/location")
    suspend fun fetch(): LocationResult
}
