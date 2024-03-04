package com.codesthetic.engine.core.episodes.data

import com.codesthetic.engine.core.episodes.domain.Episode
import com.codesthetic.engine.core.episodes.domain.EpisodeGateway
import javax.inject.Inject

/**
 * Created by razylvidal on December 21, 2023
 */
class EpisodeRepository
    @Inject
    constructor(
        private val api: EpisodeRemoteService,
        private val dao: EpisodeDao,
    ) : EpisodeGateway {
        override suspend fun fetch(): List<Episode> {
            return api.fetch().episodes.map { it.toDomain() }
        }

        override suspend fun get(): List<Episode> {
            return dao.get().map { it.toDomain() }
        }

        override suspend fun get(id: Int): Episode {
            return dao.get(id = id).toDomain()
        }

        override suspend fun save(episode: Episode) {
            dao.save(episode.toDB())
        }
    }
