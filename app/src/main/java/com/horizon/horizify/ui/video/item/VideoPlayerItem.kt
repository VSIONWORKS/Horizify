package com.horizon.horizify.ui.video.item

import android.view.View
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.horizon.horizify.R
import com.horizon.horizify.databinding.VideoPlayerItemBinding
import com.horizon.horizify.ui.video.viewmodel.VideoViewModel
import com.horizon.horizify.utils.Constants
import com.xwray.groupie.viewbinding.BindableItem

class VideoPlayerItem(val viewModel : VideoViewModel) : BindableItem<VideoPlayerItemBinding>() {

    private var player: YouTubePlayer? = null

    override fun bind(viewBinding: VideoPlayerItemBinding, position: Int) {
        with(viewBinding){

            youtubePlayerView.initialize(Constants.YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
                    if (null == p1) return
                    player = p1

                    displayCurrentTime()

                    // Start buffering
                    // insert video id
                    if (!p2) {
                        p1.cueVideo("foT0Qrif-P0")
                    }

                    p1.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS)
                }

                override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                    TODO("Not yet implemented")
                }

            })

        }
    }

    override fun getLayout(): Int = R.layout.video_player_item
    override fun initializeViewBinding(view: View): VideoPlayerItemBinding = VideoPlayerItemBinding.bind(view)

    private fun VideoPlayerItemBinding.displayCurrentTime() {
        if (null == youtubePlayerView) return
        val formattedTime: String =
            formatTime(player!!.durationMillis - player!!.currentTimeMillis)
        playTime.text = formattedTime
    }

    private fun formatTime(millis: Int): String {
        val seconds = millis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        return (if (hours == 0) "--:" else "$hours:") + String.format(
            "%02d:%02d",
            minutes % 60,
            seconds % 60
        )
    }
}