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
    if (residents.isEmpty()) return emptyList()

    return residents.map { url ->
        url.substringAfterLast('/').toInt()
    }
}

fun LocationDB.toDomain() =
    Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = unwrapIds(residents),
        url = url
    )

fun unwrapIds(ids: String?): List<Int> {
    if (ids?.isEmpty() == true) return emptyList()

    return ids!!.split(",").map { it.toInt() }
}

fun Location.toDB() =
    LocationDB(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = residents.joinToString(","),
        url = url
    )
