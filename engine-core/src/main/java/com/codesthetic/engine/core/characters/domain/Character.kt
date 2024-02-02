package com.codesthetic.engine.core.characters.domain

/**
 * Created by razylvidal on September 08, 2023
 */
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String?,
    val gender: String,
    val origin: Int,
    val location: Int,
    val image: String,
    val episode: List<Int>,
    val url: String
)
