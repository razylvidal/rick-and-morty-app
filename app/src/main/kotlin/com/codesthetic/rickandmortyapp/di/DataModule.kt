package com.codesthetic.rickandmortyapp.di

import android.content.Context
import com.codesthetic.engine.core.RickAndMortyDatabase
import com.codesthetic.engine.core.characters.data.CharacterDao
import com.codesthetic.engine.core.characters.data.CharacterRemoteService
import com.codesthetic.engine.core.characters.data.CharacterRepository
import com.codesthetic.engine.core.characters.domain.CharacterGateway
import com.codesthetic.engine.core.episodes.data.EpisodeDao
import com.codesthetic.engine.core.episodes.data.EpisodeRemoteService
import com.codesthetic.engine.core.episodes.data.EpisodeRepository
import com.codesthetic.engine.core.episodes.domain.EpisodeGateway
import com.codesthetic.engine.core.gender.data.GenderDao
import com.codesthetic.engine.core.gender.data.GenderRepository
import com.codesthetic.engine.core.gender.domain.GenderGateway
import com.codesthetic.engine.core.location.data.LocationDao
import com.codesthetic.engine.core.location.data.LocationRemoteService
import com.codesthetic.engine.core.location.data.LocationRepository
import com.codesthetic.engine.core.location.domain.LocationGateway
import com.codesthetic.engine.core.status.data.StatusDao
import com.codesthetic.engine.core.status.data.StatusRepository
import com.codesthetic.engine.core.status.domain.StatusGateway
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
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

    @Binds
    abstract fun bindGenderGateway(repo: GenderRepository): GenderGateway

    @Binds
    abstract fun bindStatusGateway(repo: StatusRepository): StatusGateway

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

        @Provides
        fun providesGenderDao(db: RickAndMortyDatabase): GenderDao {
            return db.genderDao()
        }

        @Provides
        fun providesStatusDao(db: RickAndMortyDatabase): StatusDao {
            return db.statusDao()
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object Remote {
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
