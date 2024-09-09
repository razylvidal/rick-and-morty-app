package com.codesthetic.engine.core.location.domain

/**
 * Created by razylvidal on December 21, 2023
 */
data class Location(
    val id: Int = 0,
    val name: String = "unknown",
    val type: String = "unknown",
    val dimension: String = "unknown",
    val residents: List<Int> = emptyList(),
    val url: String = "",
)
