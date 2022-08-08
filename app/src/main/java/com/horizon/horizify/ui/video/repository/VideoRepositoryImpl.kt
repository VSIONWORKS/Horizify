package com.horizon.horizify.ui.video.repository

import com.horizon.horizify.base.SharedPreference
import com.horizon.horizify.extensions.saveObject
import com.horizon.horizify.service.NetworkService
import com.horizon.horizify.ui.video.VideoPlayerActivity.Companion.PLAYLIST_ITEMS
import com.horizon.horizify.ui.video.model.PlaylistVideosModel
import com.horizon.horizify.ui.video.model.VideoPlaylistItemModel
import com.horizon.horizify.ui.video.model.VideoPlaylistModel
import com.horizon.horizify.utils.Constants

class VideoRepositoryImpl(private val service: NetworkService, private val prefs: SharedPreference) : VideoRepository {
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

    override suspend fun getPlayListItems(playlistId: String): VideoPlaylistItemModel {
        return service.getPlayListsItem(
            part = Constants.YOUTUBE_PART,
            playlistId = playlistId,
            apiKey = Constants.YOUTUBE_API_KEY
        )
    }

    override suspend fun getPlayListItemNextPage(playlistId: String, nextToken: String): VideoPlaylistItemModel {
        return service.getPlayListItemNextPage(
            part = Constants.YOUTUBE_PART,
            playlistId = playlistId,
            pageToken = nextToken,
            apiKey = Constants.YOUTUBE_API_KEY
        )
    }

    override fun savePlaylistItems(playlistItem: ArrayList<VideoPlaylistItemModel.Video>) {
        prefs.removeValue(PLAYLIST_ITEMS)
        prefs.saveObject(PLAYLIST_ITEMS, PlaylistVideosModel(videos = playlistItem))
    }
}