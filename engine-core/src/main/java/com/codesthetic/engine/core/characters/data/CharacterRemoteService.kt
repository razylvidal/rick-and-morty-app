package com.codesthetic.engine.core.characters.data

import retrofit2.http.GET

/**
 * Created by razylvidal on September 08, 2023
 */
interface CharacterRemoteService {
    @GET("/character")
    suspend fun fetch(): CharactersResult
}
