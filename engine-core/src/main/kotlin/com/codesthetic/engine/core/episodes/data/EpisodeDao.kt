package com.codesthetic.engine.core.episodes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by razylvidal on December 21, 2023
 */
@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(episode: EpisodeDB)

    @Query("SELECT * FROM episodes WHERE id =:id LIMIT 1")
    fun get(id: Int): EpisodeDB?

    @Query("SELECT * FROM episodes")
    fun get(): List<EpisodeDB>

    @Query("SELECT * FROM episodes WHERE season = :season")
    fun getBySeason(season: Int): List<EpisodeDB>

    @Query("SELECT DISTINCT(season) FROM episodes")
    fun getSeasons(): List<Int>

    @Query("SELECT COUNT(*) FROM episodes")
    fun count(): Int

    @Query("DELETE FROM episodes")
    fun clear()
}
