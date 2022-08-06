package com.horizon.horizify.ui.video.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.VideoPlayerItemBinding
import com.horizon.horizify.ui.video.viewmodel.VideoViewModel
import com.xwray.groupie.viewbinding.BindableItem

class VideoPlayerItem(val viewModel : VideoViewModel) : BindableItem<VideoPlayerItemBinding>(){
    override fun bind(viewBinding: VideoPlayerItemBinding, position: Int) {
        with(viewBinding){

        }
    }

    override fun getLayout(): Int = R.layout.video_player_item
    override fun initializeViewBinding(view: View): VideoPlayerItemBinding = VideoPlayerItemBinding.bind(view)
}