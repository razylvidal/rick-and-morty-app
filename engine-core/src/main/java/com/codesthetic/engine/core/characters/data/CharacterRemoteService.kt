package com.codesthetic.engine.core.characters.data

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by razylvidal on September 08, 2023
 */
interface CharacterRemoteService {

    @GET("/character")
    suspend fun fetch() : CharactersResult

    @GET("/character/{id}")
    suspend fun fetch(@Path("id") id: Int) : CharacterRaw
}
