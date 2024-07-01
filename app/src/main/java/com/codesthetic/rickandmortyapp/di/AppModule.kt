package com.codesthetic.rickandmortyapp.di

import android.content.Context
import com.codesthetic.engine.AppNavigator
import com.codesthetic.rickandmortyapp.AppNavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        fun provideNavigator(
            @ApplicationContext
            context: Context,
        ): AppNavigator {
            return AppNavigatorImpl(context)
        }
    }
}
