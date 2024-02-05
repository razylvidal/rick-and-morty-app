package com.codesthetic.engine.core.episodes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codesthetic.engine.core.episodes.domain.Episode

/**
 * Created by razylvidal on December 21, 2023
 */
@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(episode: Episode)

    @Query("SELECT * FROM episodes WHERE id = :id")
    fun get(id: Int): EpisodeDB

    @Query("SELECT * FROM episodes")
    fun get(): List<EpisodeDB>

    @Query("SELECT COUNT(*) FROM episodes")
    fun count(): Int

    @Query("DELETE FROM episodes")
    fun clear()
}
