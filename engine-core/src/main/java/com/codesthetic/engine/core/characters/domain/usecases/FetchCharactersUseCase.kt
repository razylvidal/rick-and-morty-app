package com.codesthetic.engine.core.characters.domain.usecases

import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.characters.domain.CharacterGateway

/**
 * Created by razylvidal on September 09, 2023
 */
class FetchCharactersUseCase(
    private val gateway: CharacterGateway
) {

    suspend fun fetch(): List<Character> {
        return gateway.get()
    }
}
