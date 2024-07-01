package com.codesthetic.engine.core.episodes.domain.usecases

import com.codesthetic.engine.core.episodes.domain.Episode
import com.codesthetic.engine.core.episodes.domain.EpisodeGateway
import com.codesthetic.engine.exception.NoSuchDataExistException
import javax.inject.Inject

/**
 * Created by razylvidal on December 21, 2023
 */
class LoadEpisodeUseCase
    @Inject
    constructor(
        private val gateway: EpisodeGateway,
        private val fetchEpisodesUseCase: FetchEpisodesUseCase,
    ) {
        suspend fun get(): List<Episode> {
            return try {
                gateway.get()
            } catch (exception: NoSuchDataExistException) {
                fetchEpisodesUseCase.fetch()
                gateway.get()
            }
        }

        suspend fun get(id: Int): Episode {
            return gateway.get(id)
        }
    }
