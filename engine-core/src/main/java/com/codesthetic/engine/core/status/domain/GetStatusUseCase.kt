package com.codesthetic.engine.core.status.domain

import javax.inject.Inject

/**
 * Created by razylvidal on July 05, 2024
 */
class GetStatusUseCase
    @Inject
    constructor(
        private val gateway: StatusGateway,
    ) {

        operator fun invoke(): Status {
            val uniqueStatus =
                gateway.getStatus().flatMap {
                    it.split(", ")
                }.distinct()
            return Status(uniqueStatus)
        }
    }
