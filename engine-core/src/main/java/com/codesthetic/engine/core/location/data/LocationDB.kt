package com.codesthetic.engine.core.location.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by razylvidal on December 27, 2023
 */

@Entity(tableName = "location")
class LocationDB(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "dimension")
    val dimension: String,

    @ColumnInfo(name = "residents")
    val residents: String,

    @ColumnInfo(name = "url")
    val url: String
)
