package com.horizon.horizify.ui.video

import android.os.Bundle
import android.view.View
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.extensions.collectOnStart
import com.horizon.horizify.extensions.launchOnStart
import com.horizon.horizify.extensions.setBody
import com.horizon.horizify.ui.video.item.VideoPlaylistItem
import com.horizon.horizify.ui.video.viewmodel.VideoViewModel
import com.horizon.horizify.utils.PageId
import com.horizon.horizify.utils.PageState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf


class VideoFragment : GroupieFragment() {

    private var initialLoad = true
    private val videoViewModel: VideoViewModel by viewModel()

    private val playlistItem by inject<VideoPlaylistItem> { parametersOf(videoViewModel) }
    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {

        with(root) {
            setBody(playlistItem)
        }
        startCollect()
    }

    override fun onStop() {
        super.onStop()
        hideLoader()
    }

    private fun startCollect() {
        with(videoViewModel) {
            state.collectOnStart(viewLifecycleOwner, playlistItem::pageState)
            pageState.collectOnStart(viewLifecycleOwner, ::handlePageState)
            launchOnStart { setup() }
        }
    }

    private fun handlePageState(state: PageState) {
        binding.apply {
            when (state) {
                PageState.Loading -> showLoader()
                PageState.Completed -> {
                    if (!initialLoad){
                        hideLoader()
                        navigateToPage(PageId.VIDEO_PLAYER)
                    }
                    initialLoad = false
                }
                PageState.Error -> hideLoader()
            }
        }
    }

}

enum class State {
    LOADING,
    ERROR,
    COMPLETE
}