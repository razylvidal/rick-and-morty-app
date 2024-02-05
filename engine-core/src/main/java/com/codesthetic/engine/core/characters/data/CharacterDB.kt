package com.codesthetic.engine.core.characters.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by razylvidal on September 09, 2023
 */

@Entity(tableName = "characters")
class CharacterDB(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "species")
    val species: String,

    @ColumnInfo(name = "type")
    val type: String?,

    @ColumnInfo(name = "gender")
    val gender: String,

    @ColumnInfo(name = "image_url")
    val imageURL: String,

    @ColumnInfo(name = "origin")
    val origin: Int,

    @ColumnInfo(name = "location")
    val location: Int,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "episodes")
    val episodes: String
)
