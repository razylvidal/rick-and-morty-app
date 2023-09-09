package com.codesthetic.rickandmortyapp.characters.data

import com.codesthetic.rickandmortyapp.characters.domain.Character
import com.codesthetic.rickandmortyapp.characters.domain.CharacterGateway

/**
 * Created by razylvidal on September 08, 2023
 */
class CharacterRepository(
    private val api : CharacterRemoteService
) : CharacterGateway {
    override suspend fun get(id: Int): Character {
       return try {
           api.fetch(id).toDomain()
       }catch (ex : Exception){
           throw ex.fillInStackTrace()
       }
    }

    override suspend fun get(): List<Character> {
        return try {
            api.fetch().characters.map { it.toDomain() }
        }catch (ex : Exception){
            throw ex.fillInStackTrace()
        }
    }
}