package com.horizon.horizify.ui.video.item

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.horizon.horizify.R
import com.horizon.horizify.databinding.VideoPlaylistItemBinding
import com.horizon.horizify.ui.video.State
import com.horizon.horizify.ui.video.viewmodel.IVideoViewModel
import com.horizon.horizify.utils.BindableItemObserver
import com.horizon.horizify.utils.ItemAction
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class VideoPlaylistItem(val viewModel: IVideoViewModel) : BindableItem<VideoPlaylistItemBinding>() {

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
        val allPlaylist = viewModel.getPlaylists()
        val playlist = allPlaylist.map {
            VideoItem(
                video = it,
                onClick = ItemAction {
                    viewModel.fetchPlaylistItems(it.id)
                }
            )
        }
        rvPlaylist.apply {
            adapter = groupAdapter.apply { addAll(playlist) }
            layoutManager = LinearLayoutManager(context)
        }
    }

}