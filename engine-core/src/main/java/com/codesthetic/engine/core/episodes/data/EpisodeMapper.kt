package com.codesthetic.engine.core.episodes.data

import com.codesthetic.engine.core.episodes.domain.Episode

/**
 * Created by razylvidal on December 21, 2023
 */
fun EpisodeResult.EpisodeRaw.toDomain() = Episode(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode,
    characters = characters.map { it.getID() },
    url = url
)

private fun String.getID(): Int {
    return this.substringAfterLast('/').toInt()
}

fun Episode.toDB() = EpisodeDB(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode,
    characters = characters.joinToString(","),
    url = url
)

fun EpisodeDB.toDomain() = Episode(
    id = id,
    name = name,
    airDate = airDate,
    characters = characters.split(",").map { it.toInt() },
    url = url,
    episode = episode
)
