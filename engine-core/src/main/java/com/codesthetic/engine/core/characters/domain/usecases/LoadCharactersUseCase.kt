package com.codesthetic.engine.core.characters.domain.usecases

import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.characters.domain.CharacterGateway
import com.codesthetic.engine.exception.NoSuchDataExistException
import javax.inject.Inject

/**
 * Created by razylvidal on September 09, 2023
 */
class LoadCharactersUseCase
    @Inject
    constructor(
        private val gateway: CharacterGateway,
        private val fetchCharactersUseCase: FetchCharactersUseCase,
    ) {
        suspend fun load(): List<Character> {
            return try {
                gateway.get()
            } catch (exception: NoSuchDataExistException) {
                fetchCharactersUseCase.fetch()
            }
        }
    }
