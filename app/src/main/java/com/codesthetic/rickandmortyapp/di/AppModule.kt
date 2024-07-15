@file:Suppress("UtilityClassWithPublicConstructor")

package com.codesthetic.rickandmortyapp.di

import com.codesthetic.engine.AppNavigator
import com.codesthetic.rickandmortyapp.AppNavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by razylvidal on December 15, 2023
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    companion object {
        @Provides
        @Singleton
        fun provideNavigator(): AppNavigator {
            return AppNavigatorImpl()
        }
    }
}
