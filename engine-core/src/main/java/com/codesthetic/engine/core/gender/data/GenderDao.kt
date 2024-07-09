package com.codesthetic.engine.core.gender.data

import androidx.room.Dao
import androidx.room.Query

/**
 * Created by razylvidal on July 05, 2024
 */
@Dao
interface GenderDao {
    @Query("SELECT DISTINCT(gender) FROM characters ORDER BY gender")
    fun getGender(): List<String>
}
