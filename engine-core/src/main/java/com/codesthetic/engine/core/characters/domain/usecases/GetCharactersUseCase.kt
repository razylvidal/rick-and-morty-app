package com.codesthetic.engine.core.characters.domain.usecases

import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.characters.domain.CharacterGateway
import javax.inject.Inject

/**
 * Created by razylvidal on September 09, 2023
 */
class GetCharactersUseCase
    @Inject
    constructor(
        private val gateway: CharacterGateway,
    ) {
        suspend fun get(): List<Character> {
            return gateway.get()
        }
    }
