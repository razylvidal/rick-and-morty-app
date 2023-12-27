package com.codesthetic.engine.core.episodes.domain

/**
 * Created by razylvidal on December 21, 2023
 */
data class Episodes(
    val episodes: List<Episode>
) {
    data class Episode(
        val id: Int,
        val name: String,
        val airDate: String,
        val episode: String,
        val characters: List<String>,
        val url: String
    )
}
