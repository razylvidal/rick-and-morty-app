package com.codesthetic.engine.core.episodes.domain

/**
 * Created by razylvidal on December 21, 2023
 */
interface EpisodeGateway {

    suspend fun getEpisodes() : Episodes
}
