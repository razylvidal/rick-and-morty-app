package com.codesthetic.rickandmortyapp.di

import com.codesthetic.engine.core.characterdisplayconfig.data.CharacterDisplayConfigRepository
import com.codesthetic.engine.core.characterdisplayconfig.domain.CharacterDisplayConfigGateway
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

/**
 * Created by razylvidal on July 05, 2024
 */
@Module
@InstallIn(ActivityComponent::class)
abstract class CharacterDisplayConfigModule {
    @Binds
    @ActivityScoped
    abstract fun bindCharacterDisplayConfigGateway(
        repo: CharacterDisplayConfigRepository,
    ): CharacterDisplayConfigGateway
}
