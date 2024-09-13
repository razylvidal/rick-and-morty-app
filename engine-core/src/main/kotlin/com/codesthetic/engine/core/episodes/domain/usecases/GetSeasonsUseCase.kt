package com.codesthetic.engine.core.episodes.domain.usecases

import com.codesthetic.engine.core.episodes.domain.EpisodeGateway
import javax.inject.Inject

class GetSeasonsUseCase
    @Inject
    constructor(private val gateway: EpisodeGateway) {
        operator fun invoke(): List<Int> {
            return gateway.getSeasons().sorted()
        }
    }
