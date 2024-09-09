package com.codesthetic.engine.core.characterdisplayconfig.data

import com.codesthetic.engine.core.characterdisplayconfig.domain.CharacterDisplayConfigGateway
import com.codesthetic.engine.core.characterdisplayconfig.domain.CharacterDisplayDataSource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

/**
 * Created by razylvidal on July 05, 2024
 */
@ActivityScoped
class CharacterDisplayConfigRepository
    @Inject
    constructor() : CharacterDisplayConfigGateway {
        private val cachedCharacterDisplay: HashMap<String, CharacterDisplayDataSource> = hashMapOf()

        override fun save(config: CharacterDisplayDataSource) {
            cachedCharacterDisplay[DISPLAY_CONFIG_TAG] = config
        }

        override fun getConfig(): CharacterDisplayDataSource {
            return cachedCharacterDisplay[DISPLAY_CONFIG_TAG] ?: CharacterDisplayDataSource()
        }

        companion object {
            const val DISPLAY_CONFIG_TAG = "character_display_filter"
        }
    }
