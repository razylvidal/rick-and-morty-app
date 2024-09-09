package com.codesthetic.rickandmortyapp

import android.os.Bundle
import com.codesthetic.engine.core.analytics.AnalyticsEvent
import com.codesthetic.engine.core.analytics.NuecaLytics
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by edwinmartinez on August 25, 2020
 */

@Singleton
class FirebaseNuecalytics
    @Inject
    constructor(
        private val analytics: FirebaseAnalytics,
        private val crashlytics: FirebaseCrashlytics,
    ) : NuecaLytics {
        override fun track(event: AnalyticsEvent) {
            val bundle = Bundle()
            for (entry in event.get().entries)
                bundle.putString(entry.key, entry.value)

            if (bundle.isEmpty) {
                analytics.logEvent(event.eventTitle, null)
            } else {
                analytics.logEvent(event.eventTitle, bundle)
            }
        }

        override fun error(event: AnalyticsEvent) {
            val bundle = Bundle()
            for (entry in event.get().entries)
                bundle.putString(entry.key, entry.value)

            if (bundle.isEmpty) {
                analytics.logEvent(event.eventTitle, null)
            } else {
                analytics.logEvent(event.eventTitle, bundle)
            }
        }

        override fun record(exception: Throwable) {
            crashlytics.recordException(exception)
        }

        override fun screen(className: String) {
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, className)
            bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, className)
            analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
        }

        override fun log(message: String) {
            crashlytics.log(message)
        }
    }
