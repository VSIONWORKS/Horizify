package com.horizon.horizify.ui.video.viewmodel

import com.horizon.horizify.ui.video.State
import com.horizon.horizify.ui.video.model.VideoPlaylistModel
import com.horizon.horizify.utils.PageState
import kotlinx.coroutines.flow.StateFlow

interface IVideoViewModel {

    val pageState: StateFlow<PageState>
    val state: StateFlow<State>

    fun setup()
    fun getPlaylists(): ArrayList<VideoPlaylistModel.ItemInfo>
    fun fetchPlaylistItems(playlistId: String)
}