package com.codesthetic.engine.core.gender.domain

/**
 * Created by razylvidal on July 05, 2024
 */
@JvmInline
value class Gender(private val value: List<String>) {
    override fun toString(): String = value.joinToString { it }

    fun get(): List<String> = listOf(Option.ALL.value) + value

    enum class Option(val value: String) {
        ALL("All"),
        FEMALE("Female"),
        MALE("Male"),
        GENDERLESS("Genderless"),
        UNKNOWN("Unknown"),
    }
}
