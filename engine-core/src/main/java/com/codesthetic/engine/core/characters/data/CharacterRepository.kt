package com.codesthetic.engine.core.characters.data

import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.characters.domain.CharacterGateway
import com.codesthetic.engine.exception.NoSuchDataExistException
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
        override suspend fun fetch(page: Int): List<Character> {
            return api.fetch(page).characters.map { it.toDomain() }
        }

        override fun get(): List<Character> {
            return dao.get().map { it.toDomain() }.ifEmpty { throw NoSuchDataExistException() }
        }

        override fun get(id: Int): Character {
            return dao.get(id)?.toDomain() ?: throw NoSuchDataExistException()
        }

        override suspend fun save(character: Character) {
            dao.save(character.toDB())
        }
    }
