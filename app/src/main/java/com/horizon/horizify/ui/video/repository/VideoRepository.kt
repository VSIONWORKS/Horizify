package com.horizon.horizify.ui.video.repository

import com.horizon.horizify.ui.video.model.VideoPlaylistModel

interface VideoRepository {

    suspend fun getPlayList(): VideoPlaylistModel
    suspend fun getPlayListNextPage(nextToken: String): VideoPlaylistModel
}