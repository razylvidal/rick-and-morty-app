package com.codesthetic.engine.core.characterdisplayconfig.domain

import javax.inject.Inject

/**
 * Created by razylvidal on July 05, 2024
 */
open class CharacterDisplayConfigHelper
    @Inject
    constructor(
        private val gateway: CharacterDisplayConfigGateway,
    ) {
        fun save(config: CharacterDisplayDataSource) {
            gateway.save(config)
        }

        fun getDisplayConfig(): CharacterDisplayDataSource = gateway.getConfig()
    }
