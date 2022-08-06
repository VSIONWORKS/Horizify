package com.horizon.horizify.ui.video

import android.os.Bundle
import android.view.View
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.extensions.setBody
import com.horizon.horizify.ui.video.item.VideoPlayerItem
import com.horizon.horizify.ui.video.viewmodel.VideoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf


class VideoFragment : GroupieFragment() {

    private val videoViewModel: VideoViewModel by viewModel()

    private val item by inject<VideoPlayerItem> { parametersOf(videoViewModel) }
    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {
        with(root) {
            setBody(item)
        }
    }
}