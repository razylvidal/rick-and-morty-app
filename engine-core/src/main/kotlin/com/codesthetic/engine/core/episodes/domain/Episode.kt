package com.codesthetic.engine.core.episodes.domain

/**
 * Created by razylvidal on December 21, 2023
 */
data class Episode(
    val id: Int,
    val season: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<Int>,
    val url: String,
)
