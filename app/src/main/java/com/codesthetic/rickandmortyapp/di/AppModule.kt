@file:Suppress("UtilityClassWithPublicConstructor")

package com.codesthetic.rickandmortyapp.di

import android.content.Context
import com.codesthetic.engine.AppNavigator
import com.codesthetic.engine.core.analytics.NuecaLytics
import com.codesthetic.rickandmortyapp.AppNavigatorImpl
import com.codesthetic.rickandmortyapp.FirebaseNuecalytics
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Binds
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
    @Binds
    abstract fun bindNuecalytics(impl: FirebaseNuecalytics): NuecaLytics

    companion object {
        @Provides
        @Singleton
        fun provideNavigator(): AppNavigator {
            return AppNavigatorImpl()
        }

        @Provides
        fun provideCrashlytics(): FirebaseCrashlytics {
            return FirebaseCrashlytics.getInstance()
        }

        @Provides
        fun provideAnalytics(
            @ApplicationContext context: Context,
        ): FirebaseAnalytics {
            return FirebaseAnalytics.getInstance(context)
        }
    }
}
