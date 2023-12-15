package com.codesthetic.engine.core.characters.domain.usecases

import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.characters.domain.CharacterGateway

/**
 * Created by razylvidal on September 09, 2023
 */
class GetCharacterUseCase(
    private val gateway: CharacterGateway
) {

    suspend fun get(id : Int) : Character {
        return gateway.get(id)
    }
}
