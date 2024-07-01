package com.codesthetic.engine.core.episodes.data

import retrofit2.http.GET

/**
 * Created by razylvidal on December 21, 2023
 */
interface EpisodeRemoteService {
    @GET("episode")
    suspend fun fetch(): EpisodeResult
}
