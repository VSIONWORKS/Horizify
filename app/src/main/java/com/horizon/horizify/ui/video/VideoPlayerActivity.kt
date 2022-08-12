package com.horizon.horizify.ui.video

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
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
import com.horizon.horizify.utils.Constants.PLAYLIST_ITEMS
import com.horizon.horizify.utils.ItemActionWithValue
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


class VideoPlayerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private lateinit var binding: ActivityVideoPlayerBinding
    private var prefs: SharedPreference? = null
    private var player: YouTubePlayer? = null
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private var playlistVideos: PlaylistVideosModel = PlaylistVideosModel()

    var videoId: String = ""
    var initialLoad = false

    var mHandler: Handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.youtubePlayerView.initialize(Constants.YOUTUBE_API_KEY, this)

        getPlaylistVideosObject()
        setUpRecyclerView()
        setUpSeekbarAndListeners()
        setUpSeekbarAndListeners()
    }

    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
        if (p1 == null) return
        player = p1

        // Start buffering
        // insert first video id
        if (!p2) player?.cueVideo(videoId)

        player?.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS)
        player?.setPlayerStateChangeListener(mPlayerStateChangeListener)
        player?.setPlaybackEventListener(mPlaybackEventListener)
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
        Log.e("Initialize Failed", p1.toString())
    }

    private fun getPlaylistVideosObject() {
        prefs = SharedPreference(this)
        val gson = Gson()
        val jsonObject = prefs?.get(PLAYLIST_ITEMS, "") ?: ""
        playlistVideos = gson.fromJson(jsonObject, PlaylistVideosModel::class.java) ?: PlaylistVideosModel()
    }

    private fun setUpRecyclerView() {
        with(binding) {
            val videos = playlistVideos.videos.map {
                VideoTrackItem(
                    video = it,
                    onClick = ItemActionWithValue { video ->
                        loadSelected(video.position, video.videoId, video.title)
                    }
                )
            }

            videoId = if (videos.isNullOrEmpty()) "" else videos.first().video.snippet.resourceId.videoId
            rvPlaylistItems.apply {
                adapter = groupAdapter.apply { addAll(videos) }
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun setInitialVideo() {
        val videos = playlistVideos.videos
        if (!videos.isNullOrEmpty()) {
            val video = videos[0]
            loadSelected(0, video.snippet.resourceId.videoId, video.snippet.title, false)
        }
    }

    private fun loadSelected(position: Int, id: String, title: String, cueVideo: Boolean = true) {
        videoId = id
        if (cueVideo) player?.cueVideo(id)
        binding.txtVideoTitle.text = title
        displayCurrentTime()
    }

    private fun setUpSeekbarAndListeners() {
        with(binding) {
            videoSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    val lengthPlayed = player!!.durationMillis * p1 / 100.toLong()
                    player!!.seekToMillis(lengthPlayed.toInt())
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}
            })

            playVideo.setOnClickListener {
                if (null != player && !player!!.isPlaying) {
                    player!!.play()
                }
            }
            pauseVideo.setOnClickListener {
                if (null != player && player!!.isPlaying) {
                    player!!.pause()
                }
            }
        }
    }

    private var mPlaybackEventListener: YouTubePlayer.PlaybackEventListener = object :
        YouTubePlayer.PlaybackEventListener {
        override fun onBuffering(arg0: Boolean) {}
        override fun onPaused() {
            mHandler!!.removeCallbacks(runnable)
        }

        override fun onPlaying() {
            mHandler!!.postDelayed(runnable, 100)
            displayCurrentTime()
        }

        override fun onSeekTo(arg0: Int) {
            mHandler!!.postDelayed(runnable, 100)
        }

        override fun onStopped() {
            mHandler!!.removeCallbacks(runnable)
        }
    }

    private var mPlayerStateChangeListener: YouTubePlayer.PlayerStateChangeListener = object :
        YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {}
        override fun onError(arg0: YouTubePlayer.ErrorReason?) {}
        override fun onLoaded(arg0: String) {
            if (!initialLoad) {
                initialLoad = true
                setInitialVideo()
            }
        }

        override fun onLoading() {}
        override fun onVideoEnded() {}
        override fun onVideoStarted() {
            displayCurrentTime()
        }
    }

    var runnable: Runnable = object : Runnable {
        override fun run() {
            displayCurrentTime()
            mHandler?.postDelayed(this, 100)
        }
    }

    private fun displayCurrentTime() {
        if (player == null) return
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