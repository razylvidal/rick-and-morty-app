package com.codesthetic.engine.core.characterdisplayconfig.domain

import com.codesthetic.engine.core.gender.domain.Gender
import com.codesthetic.engine.core.status.domain.Status

/**
 * Created by razylvidal on July 05, 2024
 */
data class CharacterDisplayDataSource(
    val gender: String = Gender.Option.ALL.value,
    val status: String = Status.ALL,
) {
    fun hasActiveFilters(): Boolean {
        return gender != Gender.Option.ALL.value || status != Status.ALL
    }
}
