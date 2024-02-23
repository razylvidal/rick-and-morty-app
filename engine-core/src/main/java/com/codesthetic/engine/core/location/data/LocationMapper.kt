package com.codesthetic.engine.core.location.data

import com.codesthetic.engine.core.location.domain.Location

/**
 * Created by razylvidal on December 27, 2023
 */

fun LocationResult.LocationRaw.toDomain() = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = residents.map { it.getID() },
    url = url
)

private fun String.getID(): Int {
    return this.substringAfterLast('/').toInt()
}

fun LocationDB.toDomain() = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = residents.split(",").map { it.toInt() },
    url = url
)

fun Location.toDB() = LocationDB(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = residents.joinToString(","),
    url = url
)
