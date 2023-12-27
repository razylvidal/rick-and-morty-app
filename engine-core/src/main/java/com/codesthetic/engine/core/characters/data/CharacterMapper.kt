package com.codesthetic.engine.core.characters.data

import com.codesthetic.engine.core.characters.domain.Character

/**
 * Created by razylvidal on September 08, 2023
 */
fun CharactersResult.CharacterRaw.toDomain() = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.toDomain(),
    location = location.toDomain(),
    image = image,
    episode = episode,
    url = url,
    created = created
)

fun CharactersResult.CharacterRaw.OriginRaw.toDomain() = Character.Origin(
    name = name,
    url = url
)

fun CharactersResult.CharacterRaw.LocationRaw.toDomain() = Character.Location(
    name = name,
    url = url
)
