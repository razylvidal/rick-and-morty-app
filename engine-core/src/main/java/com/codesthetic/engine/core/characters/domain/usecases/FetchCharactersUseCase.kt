package com.codesthetic.engine.core.characters.domain.usecases

import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.characters.domain.CharacterGateway
import javax.inject.Inject

/**
 * Created by razylvidal on September 09, 2023
 */
class FetchCharactersUseCase
    @Inject
    constructor(
        private val gateway: CharacterGateway,
    ) {
        suspend fun fetch(page: Int = 1): List<Character> {
            val characters = gateway.fetch(page)
            characters.map {
                gateway.save(it)
            }
            return characters
        }
    }
