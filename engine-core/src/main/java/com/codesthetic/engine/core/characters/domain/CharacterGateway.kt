package com.codesthetic.engine.core.characters.domain

/**
 * Created by razylvidal on September 08, 2023
 */
interface CharacterGateway {
    suspend fun fetch(page: Int): List<Character>

    suspend fun get(): List<Character>

    fun get(id: Int): Character

    suspend fun save(character: Character)
}
