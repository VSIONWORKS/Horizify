package com.horizon.horizify.ui.video

import android.os.Bundle
import android.view.View
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.extensions.collectOnStart
import com.horizon.horizify.extensions.launchOnStart
import com.horizon.horizify.extensions.setBody
import com.horizon.horizify.ui.video.item.VideoPlayerItem
import com.horizon.horizify.ui.video.item.VideoPlaylistItem
import com.horizon.horizify.ui.video.viewmodel.VideoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf


class VideoFragment : GroupieFragment() {

    private val videoViewModel: VideoViewModel by viewModel()

    private val item by inject<VideoPlayerItem> { parametersOf(videoViewModel) }
    private val playlistItem by inject<VideoPlaylistItem> { parametersOf(videoViewModel) }
    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {
        with(root) {
            setBody(playlistItem)
        }
        startCollect()
    }

    private fun startCollect() {
        with(videoViewModel) {
            pageState.collectOnStart(viewLifecycleOwner, playlistItem::pageState)
            launchOnStart { setup() }
        }
    }
}

enum class State {
    LOADING,
    ERROR,
    COMPLETE
}