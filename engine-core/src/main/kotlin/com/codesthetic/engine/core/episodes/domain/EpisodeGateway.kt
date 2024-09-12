package com.codesthetic.engine.core.episodes.domain

/**
 * Created by razylvidal on December 21, 2023
 */
interface EpisodeGateway {
    suspend fun fetch(): List<Episode>

    fun get(): List<Episode>

    fun get(id: Int): Episode

    fun getBySeason(season: Int): List<Episode>

    fun save(episode: Episode)
}
