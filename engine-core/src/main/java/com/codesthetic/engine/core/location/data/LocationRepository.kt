package com.codesthetic.engine.core.location.data

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
            var currentPage = INITIAL_PAGE
            val response = api.fetch(INITIAL_PAGE)
            val locations = response.locations.toMutableList()
            while (++currentPage <= response.info.pages) {
                locations += api.fetch(currentPage).locations
            }
            return locations.map { it.toDomain() }.toList()
        }

        override suspend fun get(): List<Location> {
            return dao.get().map { it.toDomain() }.ifEmpty { throw NoSuchDataExistException() }
        }

        override suspend fun get(id: Int): Location {
            return dao.get(id)?.toDomain() ?: throw NoSuchDataExistException()
        }

        override fun save(locations: Location) {
            dao.save(locations.toDB())
        }

        override fun clear() {
            dao.clear()
        }

        companion object {
            private const val INITIAL_PAGE = 1
        }
    }
