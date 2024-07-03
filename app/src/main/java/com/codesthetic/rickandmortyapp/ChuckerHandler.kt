package com.codesthetic.rickandmortyapp

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager

/**
 * Created by razylvidal on July 03, 2024
 */
object ChuckerHandler {
    private const val MAX_CONTENT_LENGTH = 250_000L

    fun getInterceptor(context: Context): ChuckerInterceptor {
        val chuckerCollector =
            ChuckerCollector(
                context,
                showNotification = true,
                RetentionManager.Period.ONE_WEEK
            )
        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(MAX_CONTENT_LENGTH)
            .alwaysReadResponseBody(enable = true)
            .build()
    }
}
