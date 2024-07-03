package com.codesthetic.engine.core.episodes.domain.usecases

import com.codesthetic.engine.core.episodes.domain.Episode
import com.codesthetic.engine.core.episodes.domain.EpisodeGateway
import com.codesthetic.engine.exception.NoSuchDataExistException
import javax.inject.Inject

/**
 * Created by razylvidal on July 02, 2024
 */
class GetEpisodeByIdUseCase
    @Inject
    constructor(
        private val gateway: EpisodeGateway,
    ) {
        suspend operator fun invoke(params: Param): Episode {
            return try {
                gateway.get(params.id)
            } catch (ex: NoSuchDataExistException) {
                gateway.fetch()
                gateway.get(params.id)
            }
        }

        data class Param(val id: Int)
    }
