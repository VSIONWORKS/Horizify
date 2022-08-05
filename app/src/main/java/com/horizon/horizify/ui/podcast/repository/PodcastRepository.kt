package com.horizon.horizify.ui.podcast.repository

import com.horizon.horizify.ui.podcast.model.PodcastModel

interface PodcastRepository {
    suspend fun getTrackList(): PodcastModel
}