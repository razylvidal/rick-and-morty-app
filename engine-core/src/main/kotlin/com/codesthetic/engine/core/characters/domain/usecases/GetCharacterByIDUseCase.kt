package com.codesthetic.engine.core.characters.domain.usecases

import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.characters.domain.CharacterGateway
import javax.inject.Inject

/**
 * Created by razylvidal on January 21, 2024
 */
class GetCharacterByIDUseCase
    @Inject
    constructor(
        private val gateway: CharacterGateway,
    ) {
        fun get(id: Int): Character {
            return gateway.get(id)
        }
    }
