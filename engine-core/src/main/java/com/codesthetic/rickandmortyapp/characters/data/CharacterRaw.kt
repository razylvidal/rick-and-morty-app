package com.codesthetic.rickandmortyapp.characters.data

import com.google.gson.annotations.SerializedName

/**
 * Created by razylvidal on September 08, 2023
 */
data class CharacterRaw(

    @SerializedName("id")
    val id : Int,

    @SerializedName("name")
    val name : String,

    @SerializedName("status")
    val status : String,

    @SerializedName("species")
    val species : String,

    @SerializedName("type")
    val type : String?,

    @SerializedName("gender")
    val gender : String,

    @SerializedName("origin")
    val origin : OriginRaw,

    @SerializedName("location")
    val location : LocationRaw,

    @SerializedName("image")
    val image : String,

    @SerializedName("episode")
    val episode : List<String>,

    @SerializedName("url")
    val url : String,

    @SerializedName("created")
    val created : String,
)