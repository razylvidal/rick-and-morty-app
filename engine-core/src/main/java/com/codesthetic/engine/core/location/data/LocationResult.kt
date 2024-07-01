package com.codesthetic.engine.core.location.data

import com.google.gson.annotations.SerializedName

/**
 * Created by razylvidal on December 21, 2023
 */
data class LocationResult(
    @SerializedName("results")
    val locations: List<LocationRaw>,
) {
    data class LocationRaw(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("dimension")
        val dimension: String,
        @SerializedName("residents")
        val residents: List<String>,
        @SerializedName("url")
        val url: String,
    )
}
