package com.codesthetic.engine.core.status.data

import com.codesthetic.engine.core.status.domain.StatusGateway
import com.codesthetic.engine.exception.NoSuchDataExistException
import javax.inject.Inject

/**
 * Created by razylvidal on July 05, 2024
 */
class StatusRepository
    @Inject
    constructor(
        private val dao: StatusDao,
    ) : StatusGateway {
        override fun getStatus(): List<String> {
            return dao.getStatus().ifEmpty { throw NoSuchDataExistException("No status found") }
        }
    }
