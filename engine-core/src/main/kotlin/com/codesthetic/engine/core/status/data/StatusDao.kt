package com.codesthetic.engine.core.status.data

import androidx.room.Dao
import androidx.room.Query

/**
 * Created by razylvidal on July 05, 2024
 */

@Dao
interface StatusDao {
    @Query("SELECT DISTINCT(status) FROM characters ORDER BY status")
    fun getStatus(): List<String>
}
