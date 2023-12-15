package com.codesthetic.engine.core.characters.data

import com.google.gson.annotations.SerializedName

/**
 * Created by razylvidal on September 08, 2023
 */
data class LocationRaw(

    @SerializedName("name")
    val name : String,

    @SerializedName("url")
    val url : String,
)
