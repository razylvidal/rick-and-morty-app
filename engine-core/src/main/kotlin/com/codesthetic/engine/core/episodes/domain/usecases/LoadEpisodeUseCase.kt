package com.codesthetic.engine.core.episodes.domain.usecases

import android.util.Log
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
                Log.e("NoSuchDataExistException", exception.message.toString())
                fetchEpisodesUseCase.fetch()
            }
        }
    }
