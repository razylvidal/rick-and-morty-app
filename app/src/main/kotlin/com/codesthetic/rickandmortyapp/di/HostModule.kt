package com.codesthetic.rickandmortyapp.di

import android.content.Context
import com.codesthetic.rickandmortyapp.ChuckerHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by razylvidal on July 03, 2024
 */
@Module
@InstallIn(SingletonComponent::class)
class HostModule {
    @Provides
    @Singleton
    fun providesHttpClient(
        @ApplicationContext
        context: Context,
    ): OkHttpClient {
        val client =
            OkHttpClient.Builder()
                .addInterceptor(ChuckerHandler.getInterceptor(context))

        return client.build()
    }

    @Provides
    @Singleton
    fun providesRetrofitService(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}
