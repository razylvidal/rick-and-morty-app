package com.codesthetic.rickandmortyapp.characters.domain.usecases

import com.codesthetic.rickandmortyapp.characters.domain.Character
import com.codesthetic.rickandmortyapp.characters.domain.CharacterGateway

/**
 * Created by razylvidal on September 09, 2023
 */
class FetchCharactersUseCase(
    private val gateway: CharacterGateway
) {

    suspend fun fetch() : List<Character> {
        return gateway.get()
    }
}