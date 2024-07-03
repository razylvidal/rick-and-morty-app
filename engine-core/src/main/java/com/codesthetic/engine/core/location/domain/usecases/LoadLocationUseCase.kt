package com.codesthetic.engine.core.location.domain.usecases

import android.util.Log
import com.codesthetic.engine.core.location.domain.Location
import com.codesthetic.engine.core.location.domain.LocationGateway
import com.codesthetic.engine.exception.NoSuchDataExistException
import javax.inject.Inject

/**
 * Created by razylvidal on February 05, 2024
 */
class LoadLocationUseCase
    @Inject
    constructor(
        private val gateway: LocationGateway,
        private val fetchLocationUseCase: FetchLocationUseCase,
    ) {
        suspend fun get(): List<Location> {
            return try {
                gateway.get()
            } catch (exception: NoSuchDataExistException) {
                Log.e("LoadLocationUseCase", "NoSuchDataExistException")
                fetchLocationUseCase.fetch()
            }
        }

        companion object {
            private const val DEFAULT_PAGE_SIZE = 20
        }
    }
