package com.codesthetic.engine.core.episodes.domain.usecases

import com.codesthetic.engine.core.episodes.domain.Episode
import com.codesthetic.engine.core.episodes.domain.EpisodeGateway
import javax.inject.Inject

/**
 * Created by razylvidal on December 21, 2023
 */
class FetchEpisodesUseCase @Inject constructor(
    private val gateway: EpisodeGateway
) {
    suspend fun fetch(): List<Episode> {
        return gateway.fetch()
    }
}
