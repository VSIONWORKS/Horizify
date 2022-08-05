package com.horizon.horizify.ui.podcast.model

data class PodcastModel(
    val podcastCover: String = "",
    val trackList : List<PodcastTrackModel> = emptyList()
)