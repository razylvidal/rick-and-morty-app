package com.codesthetic.engine.core.characters.data

import com.google.gson.annotations.SerializedName

/**
 * Created by razylvidal on September 08, 2023
 */
data class CharactersResult(

    @SerializedName("results")
    val characters: List<CharacterRaw>
){
    data class CharacterRaw(

        @SerializedName("id")
        val id: Int,

        @SerializedName("name")
        val name: String,

        @SerializedName("status")
        val status: String,

        @SerializedName("species")
        val species: String,

        @SerializedName("type")
        val type: String?,

        @SerializedName("gender")
        val gender: String,

        @SerializedName("origin")
        val origin: OriginRaw,

        @SerializedName("location")
        val location: LocationRaw,

        @SerializedName("image")
        val image: String,

        @SerializedName("episode")
        val episode: List<String>,

        @SerializedName("url")
        val url: String,

        @SerializedName("created")
        val created: String
    ){

        data class LocationRaw(
            @SerializedName("name")
            val name: String,

            @SerializedName("url")
            val url: String
        )

        data class OriginRaw(
            @SerializedName("name")
            val name: String,

            @SerializedName("url")
            val url: String
        )

    }
}
