package com.codesthetic.engine.core.characters.data

import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.characters.domain.CharacterGateway

/**
 * Created by razylvidal on September 08, 2023
 */
class CharacterRepository(
    private val api: CharacterRemoteService
) : CharacterGateway {

    override suspend fun get(): List<Character> {
        return try {
            api.fetch().characters.map { it.toDomain() }
        } catch (ex: Exception) {
            throw ex.fillInStackTrace()
        }
    }
}
