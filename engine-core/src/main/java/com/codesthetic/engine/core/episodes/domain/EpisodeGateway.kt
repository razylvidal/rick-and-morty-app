package com.codesthetic.engine.core.episodes.domain

/**
 * Created by razylvidal on December 21, 2023
 */
interface EpisodeGateway {
    suspend fun fetch(): List<Episode>

    suspend fun get(): List<Episode>

    suspend fun get(id: Int): Episode

    suspend fun save(episode: Episode)
}
