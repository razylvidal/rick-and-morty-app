package com.codesthetic.engine.core.characters.domain.usecases

import com.codesthetic.engine.core.characters.domain.CharacterGateway
import javax.inject.Inject

/**
 * Created by razylvidal on September 09, 2023
 */
class FetchCharactersUseCase @Inject constructor(
    private val gateway: CharacterGateway
) {
    suspend fun fetch() {
        val characters = gateway.fetch()
        characters.map{
            gateway.save(it)
        }

    }

}
