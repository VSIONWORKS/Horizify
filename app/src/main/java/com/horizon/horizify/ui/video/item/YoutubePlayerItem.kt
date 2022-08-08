package com.horizon.horizify.ui.video.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.horizon.horizify.R
import com.horizon.horizify.databinding.VideoPlayerItemBinding
import com.horizon.horizify.utils.Constants


class YoutubePlayerItem : Fragment(R.layout.video_player_item), YouTubePlayer.OnInitializedListener {

    private lateinit var binding:VideoPlayerItemBinding

    private var player: YouTubePlayer? = null

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        binding.youtubePlayerView.initialize(Constants.YOUTUBE_API_KEY, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = VideoPlayerItemBinding.inflate(inflater, container, false)
        return binding.root
    }

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

    private fun displayCurrentTime() {
        if (null == player) return
        val formattedTime: String =
            formatTime(player!!.durationMillis - player!!.currentTimeMillis)
        binding.playTime!!.text = formattedTime
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