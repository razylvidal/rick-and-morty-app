package com.codesthetic.rickandmortyapp.characters.domain

/**
 * Created by razylvidal on September 08, 2023
 */
interface CharacterGateway {

    suspend fun get(id: Int): Character

    suspend fun get(): List<Character>
}