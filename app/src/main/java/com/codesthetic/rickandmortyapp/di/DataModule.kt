package com.codesthetic.rickandmortyapp.di

import android.content.Context
import com.codesthetic.engine.core.characters.data.CharacterDao
import com.codesthetic.engine.core.characters.data.CharacterRemoteService
import com.codesthetic.engine.core.characters.data.CharacterRepository
import com.codesthetic.engine.core.characters.domain.CharacterGateway
import com.codesthetic.engine.core.episodes.data.EpisodeDao
import com.codesthetic.engine.core.episodes.data.EpisodeRemoteService
import com.codesthetic.engine.core.episodes.data.EpisodeRepository
import com.codesthetic.engine.core.episodes.domain.EpisodeGateway
import com.codesthetic.engine.core.location.data.LocationDao
import com.codesthetic.engine.core.location.data.LocationRemoteService
import com.codesthetic.engine.core.location.data.LocationRepository
import com.codesthetic.engine.core.location.domain.LocationGateway
import com.codesthetic.rickandmortyapp.RickAndMortyDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by razylvidal on December 15, 2023
 */
@Module(includes = [DataModule.Local::class, DataModule.Remote::class])
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindCharactersGateway(repo: CharacterRepository): CharacterGateway

    @Binds
    abstract fun bindEpisodeGateway(repo: EpisodeRepository): EpisodeGateway

    @Binds
    abstract fun bindLocationGateway(repo: LocationRepository): LocationGateway

    @Module
    @InstallIn(SingletonComponent::class)
    object Local {
        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context,
        ): RickAndMortyDatabase {
            return RickAndMortyDatabase.getInstance(context)
        }

        @Provides
        fun provideCharacterDao(db: RickAndMortyDatabase): CharacterDao {
            return db.characterDao()
        }

        @Provides
        fun provideEpisodeDao(db: RickAndMortyDatabase): EpisodeDao {
            return db.episodeDao()
        }

        @Provides
        fun provideLocationDao(db: RickAndMortyDatabase): LocationDao {
            return db.locationDao()
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object Remote {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"

        @Provides
        fun provideBaseUrl(): String = BASE_URL

        @Provides
        @Singleton
        fun providesRetrofitService(baseURl: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseURl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        fun provideCharacterRemoteService(retrofit: Retrofit): CharacterRemoteService {
            return retrofit.create(CharacterRemoteService::class.java)
        }

        @Provides
        fun provideEpisodeRemoteService(retrofit: Retrofit): EpisodeRemoteService {
            return retrofit.create(EpisodeRemoteService::class.java)
        }

        @Provides
        fun provideLocationRemoteService(retrofit: Retrofit): LocationRemoteService {
            return retrofit.create(LocationRemoteService::class.java)
        }
    }
}
