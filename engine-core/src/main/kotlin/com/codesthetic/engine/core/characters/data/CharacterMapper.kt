package com.codesthetic.engine.core.characters.data

import com.codesthetic.engine.core.characters.domain.Character

/**
 * Created by razylvidal on September 08, 2023
 */
fun CharactersResult.CharacterRaw.toDomain() =
    Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.url.getID(),
        location = location.url.getID(),
        image = image,
        episode = episode.map { it.getID() },
        url = url
    )

private fun String.getID(): Int {
    return if (this.isNotEmpty()) {
        this.substringAfterLast('/').toInt()
    } else {
        0
    }
}

fun Character.toDB() =
    CharacterDB(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type.orEmpty(),
        gender = gender,
        origin = origin,
        location = location,
        imageURL = image,
        episodes = episode.joinToString(separator = ","),
        url = url
    )

fun CharacterDB.toDomain() =
    Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin,
        location = location,
        image = imageURL,
        episode = episodes.split(",").map { it.toInt() },
        url = url
    )
