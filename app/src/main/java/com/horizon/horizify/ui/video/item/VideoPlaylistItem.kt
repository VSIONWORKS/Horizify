package com.horizon.horizify.ui.video.item

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.horizon.horizify.R
import com.horizon.horizify.databinding.VideoPlaylistItemBinding
import com.horizon.horizify.ui.video.State
import com.horizon.horizify.ui.video.model.VideoPlaylistModel
import com.horizon.horizify.ui.video.viewmodel.VideoViewModel
import com.horizon.horizify.utils.BindableItemObserver
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class VideoPlaylistItem(val viewModel: VideoViewModel) : BindableItem<VideoPlaylistItemBinding>() {

    var pageState by BindableItemObserver(State.LOADING)
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun bind(viewBinding: VideoPlaylistItemBinding, position: Int) {
        with(viewBinding) {
            handlePageState(pageState)
        }
    }

    override fun getLayout(): Int = R.layout.video_playlist_item

    override fun initializeViewBinding(view: View): VideoPlaylistItemBinding = VideoPlaylistItemBinding.bind(view)

    private fun VideoPlaylistItemBinding.startShimmer() {
        shimmerVideoPlaylistItemLayout.startShimmer()
    }

    private fun VideoPlaylistItemBinding.stopShimmer() {
        shimmerVideoPlaylistItemLayout.stopShimmer()
        shimmerVideoPlaylistItemLayout.isVisible = false
        rvPlaylist.isVisible = true
    }

    private fun VideoPlaylistItemBinding.handlePageState(state: State) {
        when (state) {
            State.LOADING -> startShimmer()
            State.COMPLETE -> {
                stopShimmer()
                loadPlaylist()
            }
            State.ERROR -> stopShimmer()
        }
    }

    private fun VideoPlaylistItemBinding.loadPlaylist() {
        val allPlaylist = viewModel.allPlaylist
        var playListItems = arrayListOf<VideoPlaylistModel.ItemInfo>()

        allPlaylist.forEach { playlist ->
            playlist.items?.forEach { item ->
                playListItems.add(item)
            }
        }

        val playlist = playListItems.map { VideoItem(it) }
        rvPlaylist.apply {
            adapter = groupAdapter.apply { addAll(playlist) }
            layoutManager = LinearLayoutManager(context)
        }
    }

}