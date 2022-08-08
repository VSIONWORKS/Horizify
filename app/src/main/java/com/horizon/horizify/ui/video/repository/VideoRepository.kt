package com.horizon.horizify.ui.video.repository

import com.horizon.horizify.ui.video.model.VideoPlaylistItemModel
import com.horizon.horizify.ui.video.model.VideoPlaylistModel

interface VideoRepository {

    suspend fun getPlayList(): VideoPlaylistModel
    suspend fun getPlayListNextPage(nextToken: String): VideoPlaylistModel
    suspend fun getPlayListItems(playlistId: String): VideoPlaylistItemModel
    suspend fun getPlayListItemNextPage(playlistId: String, nextToken: String): VideoPlaylistItemModel

    fun savePlaylistItems(playlistItems: ArrayList<VideoPlaylistItemModel.Video>)

//    fun savePlaylistId()
//    fun getPlaylistId()
}