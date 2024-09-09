package com.codesthetic.engine.core.gender.domain

import javax.inject.Inject

/**
 * Created by razylvidal on July 05, 2024
 */
class GetGenderUseCase
    @Inject
    constructor(
        private val gateway: GenderGateway,
    ) {
        operator fun invoke(): Gender {
            val uniqueGender =
                gateway.getGender().flatMap {
                    it.split(", ")
                }.distinct()
            return Gender(uniqueGender)
        }
    }
