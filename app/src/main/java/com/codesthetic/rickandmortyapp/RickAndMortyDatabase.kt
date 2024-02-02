package com.codesthetic.rickandmortyapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codesthetic.engine.core.characters.data.CharacterDB
import com.codesthetic.engine.core.characters.data.CharacterDao

/**
 * Created by razylvidal on January 20, 2024
 */

@Database(
    entities = [
               CharacterDB::class
               ],
    version = 1,
    exportSchema = true
)
abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract fun characterDao() : CharacterDao

    companion object {
        private const val DB_NAME = "rickandmorty.db"

        @Volatile
        private var instance: RickAndMortyDatabase? = null

        @JvmStatic
        @Synchronized
        fun getInstance(context: Context): RickAndMortyDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, RickAndMortyDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }

        fun destroyInstance() {
            instance = null
        }
    }
}