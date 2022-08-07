package com.horizon.horizify.ui.video.viewmodel

import com.horizon.horizify.base.BaseViewModel
import com.horizon.horizify.ui.video.State
import com.horizon.horizify.ui.video.model.VideoPlaylistModel
import com.horizon.horizify.ui.video.repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow

class VideoViewModel(private val repository: VideoRepository) : BaseViewModel(), IVideoViewModel {

    override val pageState = MutableStateFlow(State.LOADING)
    var playlist: VideoPlaylistModel = VideoPlaylistModel()
    var allPlaylist: ArrayList<VideoPlaylistModel> = arrayListOf()

    override fun setup() {
        safeLaunch(Dispatchers.IO) {
            playlist = repository.getPlayList()
            loadAllPlaylistPages()
        }
    }

    private fun loadAllPlaylistPages() {
        if (playlist != null && playlist.nextPageToken != null) {
            allPlaylist.add(playlist)
            loadNextPage(playlist.nextPageToken)
        }
    }

    private fun loadNextPage(nextPageToken: String?) {
        safeLaunch(Dispatchers.IO) {
            if (nextPageToken != null) {
                val nextPlaylist = repository.getPlayListNextPage(nextPageToken)
                allPlaylist.add(nextPlaylist)
                loadNextPage(nextPlaylist.nextPageToken)
            } else pageState.value = State.COMPLETE
        }
    }

}