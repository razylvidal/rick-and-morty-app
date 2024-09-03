package com.codesthetic.engine.core.analytics

/**
 * Created by ralpheufracio on 5/16/24.
 */

interface NuecaLytics {
    fun track(event: AnalyticsEvent)

    fun error(event: AnalyticsEvent)

    fun record(exception: Throwable)

    fun screen(className: String)

    fun log(message: String)
}
