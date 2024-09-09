package com.codesthetic.engine.core.location.data

import com.codesthetic.engine.core.location.domain.Location

/**
 * Created by razylvidal on December 27, 2023
 */

fun LocationResult.LocationRaw.toDomain() =
    Location(
        id = id,
        name = name,
        type = type.orEmpty(),
        dimension = dimension.orEmpty(),
        residents = getID(residents),
        url = url
    )

private fun getID(residents: List<String>): List<Int> {
    return if (residents.isEmpty()) {
        residents.map { it.substringAfterLast('/').toInt() }
    } else {
        emptyList()
    }
}

fun LocationDB.toDomain() =
    Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = emptyList(),
        url = url
    )

fun Location.toDB() =
    LocationDB(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = residents.joinToString(","),
        url = url
    )
