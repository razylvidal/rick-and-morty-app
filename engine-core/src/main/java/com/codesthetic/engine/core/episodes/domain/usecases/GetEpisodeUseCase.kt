package com.codesthetic.engine.core.episodes.domain.usecases

import com.codesthetic.engine.core.episodes.domain.Episode
import com.codesthetic.engine.core.episodes.domain.EpisodeGateway
import javax.inject.Inject

/**
 * Created by razylvidal on December 21, 2023
 */
class GetEpisodeUseCase
    @Inject
    constructor(
        private val gateway: EpisodeGateway,
        private val fetchEpisodesUseCase: FetchEpisodesUseCase,
    ) {
        suspend fun get(): List<Episode> {
            return try {
                gateway.get()
            } catch (exception: Exception) {
                fetchEpisodesUseCase.fetch()
                gateway.get()
            }
        }

        suspend fun get(id: Int): Episode {
            return gateway.get(id)
        }
    }
