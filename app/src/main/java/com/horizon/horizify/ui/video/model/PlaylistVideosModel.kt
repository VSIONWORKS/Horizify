package com.horizon.horizify.ui.video.model

import kotlinx.serialization.Serializable

@Serializable
class PlaylistVideosModel(
    val videos: ArrayList<VideoPlaylistItemModel.Video> = arrayListOf()
)