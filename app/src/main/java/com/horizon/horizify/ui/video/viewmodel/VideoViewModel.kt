package com.horizon.horizify.ui.video.viewmodel

import com.horizon.horizify.base.BaseViewModel
import com.horizon.horizify.ui.video.State
import com.horizon.horizify.ui.video.model.VideoPlaylistItemModel
import com.horizon.horizify.ui.video.model.VideoPlaylistModel
import com.horizon.horizify.ui.video.repository.VideoRepository
import com.horizon.horizify.utils.PageState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow

class VideoViewModel(private val repository: VideoRepository) : BaseViewModel(), IVideoViewModel {

    override val pageState = MutableStateFlow<PageState>(PageState.Completed)
    override val state = MutableStateFlow(State.LOADING)

    private var allPlaylist: ArrayList<VideoPlaylistModel> = arrayListOf()
    var allPlaylistItems: ArrayList<VideoPlaylistItemModel> = arrayListOf()

    override fun setup() {
        safeLaunch(Dispatchers.IO) {
            val playlist = repository.getPlayList()
            allPlaylist.add(playlist)
            if (playlist.nextPageToken != null) {
                loadNextPage(playlist.nextPageToken)
            }
        }
    }

    private fun loadNextPage(nextPageToken: String?) {
        safeLaunch(Dispatchers.IO) {
            if (nextPageToken != null) {
                val nextPlaylist = repository.getPlayListNextPage(nextPageToken)
                allPlaylist.add(nextPlaylist)
                loadNextPage(nextPlaylist.nextPageToken)
            } else state.value = State.COMPLETE
        }
    }

    override fun fetchPlaylistItems(playlistId: String) {
        pageState.value = PageState.Loading
        allPlaylistItems.clear()
        safeLaunch(Dispatchers.IO) {
            val playlistItems = repository.getPlayListItems(playlistId)
            allPlaylistItems.add(playlistItems)
            if (playlistItems.nextPageToken != null) fetchNextPlaylistItems(playlistId, playlistItems.nextPageToken)
            else {
                repository.savePlaylistItems(getPlaylistItems())
                pageState.value = PageState.Completed
            }
        }
    }

    private fun fetchNextPlaylistItems(playlistId: String, nextPageToken: String?) {
        safeLaunch(Dispatchers.IO) {
            val playlistItems = repository.getPlayListItemNextPage(playlistId, nextPageToken!!)
            allPlaylistItems.add(playlistItems)
            if (playlistItems.nextPageToken != null) fetchNextPlaylistItems(playlistId, playlistItems.nextPageToken)
            else {
                repository.savePlaylistItems(getPlaylistItems())
                pageState.value = PageState.Completed
            }
        }
    }

    override fun getPlaylists(): ArrayList<VideoPlaylistModel.ItemInfo> {
        var playLists = arrayListOf<VideoPlaylistModel.ItemInfo>()

        allPlaylist.forEach { playlist ->
            playlist.items?.forEach { item ->
                playLists.add(item)
            }
        }
        return playLists
    }

    private fun getPlaylistItems(): ArrayList<VideoPlaylistItemModel.Video> {
        var playListItems = arrayListOf<VideoPlaylistItemModel.Video>()
        allPlaylistItems.forEach { playlist ->
            playlist.items?.forEach { item ->
                playListItems.add(item)
            }
        }
        return playListItems
    }
}