package com.codesthetic.rickandmortyapp.characters.data

import com.codesthetic.rickandmortyapp.characters.domain.Character
import com.codesthetic.rickandmortyapp.characters.domain.Location
import com.codesthetic.rickandmortyapp.characters.domain.Origin

/**
 * Created by razylvidal on September 08, 2023
 */
fun CharacterRaw.toDomain() = Character(
    id = id,
    name = name,
    status = status,
    species= species,
    type = type,
    gender = gender,
    origin = origin.toDomain(),
    location = location.toDomain(),
    image = image,
    episode = episode,
    url = url,
    created = created
)

fun OriginRaw.toDomain() = Origin(
    name = name,
    url = url
)

fun LocationRaw.toDomain() = Location(
    name = name,
    url = url
)