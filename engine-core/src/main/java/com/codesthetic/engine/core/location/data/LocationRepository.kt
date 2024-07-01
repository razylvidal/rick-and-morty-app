package com.codesthetic.engine.core.location.data

import android.util.Log
import com.codesthetic.engine.core.location.domain.Location
import com.codesthetic.engine.core.location.domain.LocationGateway
import com.codesthetic.engine.exception.NoSuchDataExistException
import javax.inject.Inject

/**
 * Created by razylvidal on December 21, 2023
 */
class LocationRepository
    @Inject
    constructor(
        private val api: LocationRemoteService,
        private val dao: LocationDao,
    ) : LocationGateway {
        override suspend fun fetch(): List<Location> {
            Log.e("Location", "location")
            val result = api.fetch().locations.map { it.toDomain() }
            Log.e("Location", "success")
            return result
        }

        override suspend fun get(): List<Location> {
            return dao.get().map { it.toDomain() }.ifEmpty { throw NoSuchDataExistException() }
        }

        override suspend fun get(id: Int): Location {
            return dao.get(id).toDomain()
        }

        override suspend fun save(locations: List<Location>) {
            locations.map { dao.save(it.toDB()) }
        }

        override fun clear() {
            dao.clear()
        }
    }
