package com.codesthetic.engine.core.characters.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by razylvidal on September 09, 2023
 */

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters WHERE id = :id")
    fun get(id: Int): CharacterDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(character: CharacterDB)

    @Query("SELECT COUNT(*) FROM characters")
    fun count(): Int

    @Query("DELETE FROM characters")
    fun clear()
}
