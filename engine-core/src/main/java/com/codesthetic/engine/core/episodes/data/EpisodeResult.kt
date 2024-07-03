package com.codesthetic.engine.core.episodes.data

import com.google.gson.annotations.SerializedName

/**
 * Created by razylvidal on December 20, 2023
 */
data class EpisodeResult(
    @SerializedName("info")
    val info: InfoRaw,
    @SerializedName("results")
    val episodes: List<EpisodeRaw>,
) {
    data class InfoRaw(
        @SerializedName("count")
        val count: Int,
        @SerializedName("pages")
        val pages: Int,
    )

    data class EpisodeRaw(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("air_date")
        val airDate: String,
        @SerializedName("episode")
        val episode: String,
        @SerializedName("characters")
        val characters: List<String>,
        @SerializedName("url")
        val url: String,
    )
}
