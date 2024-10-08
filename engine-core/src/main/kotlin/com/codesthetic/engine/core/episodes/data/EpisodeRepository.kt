package com.codesthetic.engine.core.episodes.data

import com.codesthetic.engine.core.episodes.domain.Episode
import com.codesthetic.engine.core.episodes.domain.EpisodeGateway
import com.codesthetic.engine.exception.NoSuchDataExistException
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
            var currentPage = INITIAL_PAGE
            val response = api.fetch(INITIAL_PAGE)
            val episodes = response.episodes.toMutableList()
            while (++currentPage <= response.info.pages) {
                episodes += api.fetch(currentPage).episodes
            }
            return episodes.map { it.toDomain() }.toList()
        }

        override fun get(): List<Episode> {
            return dao.get().map { it.toDomain() }.ifEmpty { throw NoSuchDataExistException("No episodes found") }
        }

        override fun get(id: Int): Episode {
            return dao.get(id = id)?.toDomain() ?: throw NoSuchDataExistException("No episode found. $id")
        }

        override fun getBySeason(season: Int): List<Episode> {
            return dao.getBySeason(season = season).map { it.toDomain() }
        }

        override fun getSeasons(): List<Int> {
            return dao.getSeasons()
        }

        override fun save(episode: Episode) {
            dao.save(episode.toDB())
        }

        companion object {
            private const val INITIAL_PAGE = 1
        }
    }
