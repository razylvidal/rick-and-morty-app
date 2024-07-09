package com.codesthetic.engine.core.status.domain

/**
 * Created by razylvidal on July 05, 2024
 */
interface StatusGateway {
    fun getStatus(): List<String>
}
