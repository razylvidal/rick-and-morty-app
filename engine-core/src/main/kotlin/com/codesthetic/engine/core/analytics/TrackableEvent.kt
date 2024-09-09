package com.codesthetic.engine.core.analytics

/**
 * Created by edwinmartinez on February 27, 2020
 */
class TrackableEvent private constructor(
    eventTitle: String,
) : AnalyticsEvent(eventTitle) {
    private var map: HashMap<String, String> = hashMapOf()

    constructor(builder: Builder) : this(builder.eventTitle) {
        this.map = builder.map
    }

    override fun get(): HashMap<String, String> {
        return map
    }

    class Builder(val eventTitle: String) {
        internal val map: HashMap<String, String> = hashMapOf()

        fun add(
            key: String,
            value: String,
        ): Builder {
            map[key] = value
            return this
        }

        fun build(): TrackableEvent {
            return TrackableEvent(this)
        }
    }
}
