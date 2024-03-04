package com.codesthetic.engine.core.characters.data

import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.characters.domain.CharacterGateway
import javax.inject.Inject

/**
 * Created by razylvidal on September 08, 2023
 */
class CharacterRepository
    @Inject
    constructor(
        private val api: CharacterRemoteService,
        private val dao: CharacterDao,
    ) : CharacterGateway {
        override suspend fun fetch(): List<Character> {
            return api.fetch().characters.map { it.toDomain() }
        }

        override suspend fun get(): List<Character> {
            return try {
                api.fetch().characters.map { it.toDomain() }
            } catch (ex: Exception) {
                throw ex.fillInStackTrace()
            }
        }

        override fun get(id: Int): Character {
            return dao.get(id).toDomain()
        }

        override suspend fun save(character: Character) {
            dao.save(character.toDB())
        }
    }
