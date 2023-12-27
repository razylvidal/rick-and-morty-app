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
    residents = residents,
    url = url
)
