package com.codesthetic.engine.core.episodes.domain.usecases

import com.codesthetic.engine.core.episodes.domain.Episode
import com.codesthetic.engine.core.episodes.domain.EpisodeGateway
import javax.inject.Inject

class GetEpisodesBySeason
    @Inject
    constructor(
        private val gateway: EpisodeGateway,
    ) {
        operator fun invoke(param: Param): List<Episode> {
            return gateway.getBySeason(season = param.season)
        }

        data class Param(val season: Int)
    }
