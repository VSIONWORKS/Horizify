package com.horizon.horizify.ui.video.repository

import com.horizon.horizify.service.NetworkService
import com.horizon.horizify.ui.video.model.VideoPlaylistModel
import com.horizon.horizify.utils.Constants

class VideoRepositoryImpl(private val service: NetworkService) : VideoRepository {
    override suspend fun getPlayList(): VideoPlaylistModel {
        return service.getPlayLists(
            part = Constants.YOUTUBE_PART,
            channelId = Constants.YOUTUBE_HORIZON_CHANNEL_ID,
            apiKey = Constants.YOUTUBE_API_KEY
        )

    }

    override suspend fun getPlayListNextPage(nextToken: String): VideoPlaylistModel {
        return service.getPlayListNextPage(
            part = Constants.YOUTUBE_PART,
            channelId = Constants.YOUTUBE_HORIZON_CHANNEL_ID,
            pageToken = nextToken,
            apiKey = Constants.YOUTUBE_API_KEY
        )
    }
}