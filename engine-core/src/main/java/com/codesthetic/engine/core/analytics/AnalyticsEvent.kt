package com.codesthetic.engine.core.analytics

/**
 * Created by edwinmartinez on February 04, 2020
 */
abstract class AnalyticsEvent(val eventTitle: String) {
    abstract fun get(): HashMap<String, String>
}
