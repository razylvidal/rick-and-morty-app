package com.codesthetic.engine.core.characterdisplayconfig.domain

import com.codesthetic.engine.core.gender.domain.Gender
import com.codesthetic.engine.core.status.domain.Status

/**
 * Created by razylvidal on July 05, 2024
 */
data class CharacterDisplayDataSource(
    val gender: String = Gender.Option.ALL.value,
    val status: String = Status.ALL,
    val sortBy: SortByName = SortByName.DEFAULT,
) {
    fun hasActiveFilters(): Boolean {
        return gender != Gender.Option.ALL.value || status != Status.ALL
    }

    fun isDefault(params: Params): Boolean {
        return when (params) {
            Params.Gender -> gender == Gender.Option.ALL.value
            Params.SortBy -> sortBy == SortByName.DEFAULT
            Params.Status -> status == Status.ALL
        }
    }

    sealed class Params {
        data object Gender : Params()

        data object Status : Params()

        data object SortBy : Params()
    }

    enum class SortByName {
        DEFAULT,
        ASCENDING,
        DESCENDING,
        ;

        fun identify(value: String): SortByName? {
            return entries.firstOrNull { value.contentEquals(it.name) }
        }
    }
}
