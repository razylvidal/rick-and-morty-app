package com.codesthetic.engine.core.characters.domain.usecases

import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.characters.domain.CharacterGateway
import javax.inject.Inject

/**
 * Created by razylvidal on July 10, 2024
 */
class SearchCharacterUseCase
    @Inject
    constructor(
        private val gateway: CharacterGateway,
    ) {
        suspend operator fun invoke(param: Param): Response {
            return Response(gateway.search(param.query))
        }

        data class Response(val searchResult: List<Character>)

        data class Param(val query: String)
    }
