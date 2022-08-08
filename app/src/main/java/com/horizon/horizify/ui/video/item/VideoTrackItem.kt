package com.horizon.horizify.ui.video.item

import android.view.View
import androidx.core.view.isVisible
import com.horizon.horizify.R
import com.horizon.horizify.databinding.VideoTrackItemBinding
import com.horizon.horizify.extensions.load
import com.horizon.horizify.ui.video.model.VideoPlaylistItemModel
import com.xwray.groupie.viewbinding.BindableItem

class VideoTrackItem(val video : VideoPlaylistItemModel.Video) : BindableItem<VideoTrackItemBinding>() {
    override fun bind(viewBinding: VideoTrackItemBinding, position: Int) {
        with(viewBinding){
            if (video.snippet.thumbnails.maxres != null) imgVideo.load(video.snippet.thumbnails.maxres.url!!)
            else imgVideo.load(video.snippet.thumbnails.high.url!!)

            txtTitle.text = video.snippet.title
            if (video.snippet.description.isEmpty()) txtSpeaker.isVisible = false
            else txtSpeaker.text = video.snippet.publishedAt
//
//            layoutVideoItem.setOnClickListener {
//                onClick.actionCallback.invoke()
//            }
        }
    }

    override fun getLayout(): Int = R.layout.video_track_item

    override fun initializeViewBinding(view: View): VideoTrackItemBinding = VideoTrackItemBinding.bind(view)
}