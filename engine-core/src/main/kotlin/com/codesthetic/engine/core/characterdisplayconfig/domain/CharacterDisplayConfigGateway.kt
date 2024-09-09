package com.codesthetic.engine.core.characterdisplayconfig.domain

/**
 * Created by razylvidal on July 05, 2024
 */
interface CharacterDisplayConfigGateway {
    fun save(config: CharacterDisplayDataSource)

    fun getConfig(): CharacterDisplayDataSource
}
