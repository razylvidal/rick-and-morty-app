package com.codesthetic.engine.core.episodes.data

import com.codesthetic.engine.core.episodes.domain.EpisodeGateway
import com.codesthetic.engine.core.episodes.domain.Episodes
import javax.inject.Inject

/**
 * Created by razylvidal on December 21, 2023
 */
class EpisodeRepository @Inject constructor(
    val api : EpisodeRemoteService
) : EpisodeGateway {
    override suspend fun getEpisodes(): Episodes {
        return api.fetch().toDomain()
    }
}
