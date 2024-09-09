package com.codesthetic.engine.core.gender.data

import com.codesthetic.engine.core.gender.domain.GenderGateway
import com.codesthetic.engine.exception.NoSuchDataExistException
import javax.inject.Inject

/**
 * Created by razylvidal on July 05, 2024
 */
class GenderRepository
    @Inject
    constructor(
        private val dao: GenderDao,
    ) : GenderGateway {
        override fun getGender(): List<String> {
            return dao.getGender().ifEmpty { throw NoSuchDataExistException("No gender found") }
        }
    }
