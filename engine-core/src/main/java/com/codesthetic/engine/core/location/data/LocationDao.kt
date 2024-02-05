package com.codesthetic.engine.core.location.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by razylvidal on December 27, 2023
 */

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(location : LocationDB)

    @Query("SELECT * FROM location")
    fun get() : List<LocationDB>

    @Query("SELECT * FROM location WHERE id=:id")
    fun get(id : Int) : LocationDB

    @Query("DELETE FROM location")
    fun clear()
}
