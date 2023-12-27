package com.codesthetic.engine.core.episodes.data

import com.codesthetic.engine.core.episodes.domain.Episodes

/**
 * Created by razylvidal on December 21, 2023
 */
fun EpisodeResult.EpisodeRaw.toDomain() = Episodes.Episode(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode,
    characters = characters,
    url = url
)

fun EpisodeResult.toDomain() = Episodes(
    episodes = episodes.map { it.toDomain() }
)
