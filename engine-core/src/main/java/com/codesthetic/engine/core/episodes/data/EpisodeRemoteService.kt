package com.codesthetic.engine.core.episodes.data

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by razylvidal on December 21, 2023
 */
interface EpisodeRemoteService {
    @GET("episode")
    suspend fun fetch(
        @Query("page") page: Int,
    ): EpisodeResult
}
