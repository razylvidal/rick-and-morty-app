package com.codesthetic.engine.core.location.domain

/**
 * Created by razylvidal on December 21, 2023
 */
data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<Int>,
    val url: String
)
