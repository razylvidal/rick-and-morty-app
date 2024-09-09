package com.codesthetic.engine.core.location.data

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by razylvidal on December 21, 2023
 */
interface LocationRemoteService {
    @GET("location")
    suspend fun fetch(
        @Query("page") page: Int,
    ): LocationResult
}
