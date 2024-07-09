package com.codesthetic.engine.core.status.domain

/**
 * Created by razylvidal on July 05, 2024
 */
@JvmInline
value class Status(private val value: List<String>) {
    override fun toString(): String = value.joinToString { it }

    fun get(): List<String> = listOf(ALL) + value.sorted()

    companion object {
        const val ALL = "All"
    }
}
