package com.codesthetic.engine.core.episodes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by razylvidal on December 21, 2023
 */

@Entity(tableName = "episodes")
class EpisodeDB(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "season")
    val season: Int,
    @ColumnInfo(name = "air_date")
    val airDate: String,
    @ColumnInfo(name = "episode")
    val episode: String,
    @ColumnInfo(name = "characters")
    val characters: String,
    @ColumnInfo(name = "url")
    val url: String,
)
