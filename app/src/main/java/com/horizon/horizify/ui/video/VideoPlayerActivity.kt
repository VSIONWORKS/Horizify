package com.horizon.horizify.ui.video

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.gson.Gson
import com.horizon.horizify.base.SharedPreference
import com.horizon.horizify.databinding.ActivityVideoPlayerBinding
import com.horizon.horizify.extensions.get
import com.horizon.horizify.ui.video.item.VideoTrackItem
import com.horizon.horizify.ui.video.model.PlaylistVideosModel
import com.horizon.horizify.utils.Constants
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


class VideoPlayerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private lateinit var binding: ActivityVideoPlayerBinding
    private var prefs: SharedPreference? = null
    private var player: YouTubePlayer? = null
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private var playlistVideos: PlaylistVideosModel = PlaylistVideosModel()

    companion object {
        const val PLAYLIST_ITEMS = "playlistItems"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPlaylistVideosObject()
        setUpRecyclerView()
        binding.youtubePlayerView.initialize(Constants.YOUTUBE_API_KEY, this)
    }

    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
        if (null == p1) return
        player = p1

        displayCurrentTime()

        // Start buffering
        // insert video id
//        if (!p2) {
//            val playlistId = intent.extras?.getString(PLAYLIST_ID) ?: ""
//            p1.cueVideo("foT0Qrif-P0")
//        }

        p1.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS)

    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {

    }

    private fun getPlaylistVideosObject() {
        prefs = SharedPreference(this)
        val gson = Gson()
        val jsonObject = prefs?.get(PLAYLIST_ITEMS, "") ?: ""
        playlistVideos = gson.fromJson(jsonObject, PlaylistVideosModel::class.java) ?: PlaylistVideosModel()
    }

    private fun setUpRecyclerView() {
        with(binding){
            val videos = playlistVideos.videos.map {
                VideoTrackItem(it)
            }
            rvPlaylistItems.apply {
                adapter = groupAdapter.apply { addAll(videos) }
                layoutManager = LinearLayoutManager(context)
            }
        }
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