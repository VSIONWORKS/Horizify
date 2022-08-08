package com.horizon.horizify.ui.video.item

import android.view.View
import androidx.core.view.isVisible
import com.horizon.horizify.R
import com.horizon.horizify.databinding.VideoItemBinding
import com.horizon.horizify.extensions.load
import com.horizon.horizify.ui.video.model.VideoPlaylistModel
import com.horizon.horizify.utils.ItemAction
import com.xwray.groupie.viewbinding.BindableItem

class VideoItem(val video: VideoPlaylistModel.ItemInfo, var onClick:ItemAction ) : BindableItem<VideoItemBinding>() {

    override fun bind(viewBinding: VideoItemBinding, position: Int) {
        with(viewBinding) {
            if (video.snippet.thumbnails.maxres != null) imgVideo.load(video.snippet.thumbnails.maxres.url!!)
            else imgVideo.load(video.snippet.thumbnails.high.url!!)

            txtTitle.text = video.snippet.title
            if (video.snippet.description.isEmpty()) txtSpeaker.isVisible = false
            else txtSpeaker.text = video.snippet.description

            layoutVideoItem.setOnClickListener {
                onClick.actionCallback.invoke()
            }
        }
    }

    override fun getLayout(): Int = R.layout.video_item

    override fun initializeViewBinding(view: View): VideoItemBinding = VideoItemBinding.bind(view)
}