package com.horizon.horizify.ui.podcast

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.horizon.horizify.R
import com.horizon.horizify.base.currentSeconds
import com.horizon.horizify.base.seconds
import com.horizon.horizify.databinding.PodcastTrackListItemBinding
import com.horizon.horizify.extensions.collectOnStart
import com.horizon.horizify.extensions.launchOnStart
import com.horizon.horizify.extensions.load
import com.horizon.horizify.extensions.toast
import com.horizon.horizify.ui.podcast.item.PodcastPlayerItem
import com.horizon.horizify.ui.podcast.model.PodcastTrackModel
import com.horizon.horizify.ui.podcast.viewmodel.PodcastViewModel
import com.horizon.horizify.utils.ItemActionWithPositionAndValue
import com.horizon.horizify.utils.PageState
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs


class PodcastTrackListFragment : Fragment(R.layout.podcast_track_list_item) {

    private lateinit var binding: PodcastTrackListItemBinding
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val podcastViewModel: PodcastViewModel by viewModel()

    private lateinit var runnable: Runnable
    private var player: MediaPlayer = MediaPlayer()
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var isReady = false
    private var selectedPosition = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PodcastTrackListItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startCollect()
        setUpAppBarEffect()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        player.release()
    }

    private fun startCollect() {
        with(podcastViewModel) {
            pageState.collectOnStart(viewLifecycleOwner, ::handlePageState)
            launchOnStart { setup() }
        }
    }

    private fun setupPodcastRecyclerView() {
        with(binding) {
            val trackList = podcastViewModel.trackListItemData.trackList
            val tracks = trackList.map {
                PodcastPlayerItem(
                    track = it,
                    onClickTrackItem = ItemActionWithPositionAndValue { pos, track -> selectTrack(pos, track) }
                )
            }

            imgPodcastTrackCover.load(podcastViewModel.trackListItemData.podcastCover)
            includeTracklist.rvPodcast.apply {
                adapter = groupAdapter.apply {
                    addAll(tracks)
                }
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }

            selectTrack(selectedPosition, trackList[selectedPosition])
        }
    }

    private fun setUpPodcastPlayer() {
        player.apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }
    }

    private fun setUpPlayerControls() {
        with(binding) {
            buttonPlay.setOnClickListener { setUpPlayButton() }
            buttonPlayTop.setOnClickListener { setUpPlayButton() }

            setUpSeekBar()
            setUpPreviousNextButton()
        }
    }

    private fun setUpPlayButton() {
        with(binding) {
            if (!isReady) {
                root.context.toast("Podcast not ready")
                return
            }
            if (player.isPlaying) {
                player.pause()
                txtDuration.text = "Paused"
                handler.removeCallbacks(runnable)
                buttonPlay.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
                buttonPlayTop.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
            } else {
                player.start()
                txtDuration.text = "Now Playing"
                initPlayerSeekBar()
                player.setOnCompletionListener {
                    root.context.toast("end")
                }
                buttonPlay.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
                buttonPlayTop.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
            }
        }
    }

    private fun setUpSeekBar() {
        with(binding) {
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if (p2) {
                        player.seekTo(p1 * 1000)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}
            })

            seekBarTop.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if (p2) {
                        player.seekTo(p1 * 1000)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}
            })
        }
    }

    private fun initPlayerSeekBar() {
        with(binding) {
            seekBar.max = player.seconds
            seekBarTop.max = player.seconds
            runnable = Runnable {
                seekBar.progress = player.currentSeconds
                seekBarTop.progress = player.currentSeconds

                txtPass.text = convertSecondsToTime(player.currentSeconds)
                val diff = player.seconds - player.currentSeconds
                txtDue.text = convertSecondsToTime(diff)

                handler.postDelayed(runnable, 1000)
            }
            handler.postDelayed(runnable, 1000)
        }
    }

    private fun setUpPreviousNextButton() {
        val trackList = podcastViewModel.trackListItemData.trackList

        with(binding) {
            buttonPrev.setOnClickListener {
                if (selectedPosition == 0) return@setOnClickListener
                val position = selectedPosition - 1
                selectTrack(position, trackList[position])
            }
            buttonNext.setOnClickListener {
                if (selectedPosition == trackList.size - 1) return@setOnClickListener
                val position = selectedPosition + 1
                selectTrack(position, trackList[position])
            }
        }

    }

    private fun selectTrack(position: Int, track: PodcastTrackModel) {
        with(binding) {
            isReady = false
            selectedPosition = position

            txtDuration.text = "Buffering.."
            txtPass.text = "00:00"
            txtDue.text = "00:00"
            seekBar.progress = 0
            seekBarTop.progress = 0

            imgSelectedTrack.load(track.cover)
            imgPodcastTrackCover.load(track.cover)
            txtPlayerTitle.text = track.title
            txtTrackTitle.text = track.title
            txtTrackSpeaker.text = track.pastor
            buttonPlay.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
            buttonPlayTop.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)

            lifecycleScope.launch(Dispatchers.IO) {
                if (player.isPlaying) {
                    player.stop()
                    handler.removeCallbacks(runnable)
                }
                player.apply {
                    reset()
                    setDataSource(track.mp3URL)
                    prepare()
                }

                lifecycleScope.launch(Dispatchers.Main) {
                    isReady = true
                    txtDuration.text = "Ready"
                    player.currentPosition
                    txtPass.text = convertSecondsToTime(player.currentSeconds)
                    val diff = player.seconds - player.currentSeconds
                    txtDue.text = convertSecondsToTime(diff)
                }
            }.start()
        }
    }

    private fun setUpAppBarEffect() {
        with(binding) {
            appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                var maxOffset = appBarLayout?.totalScrollRange
                if (abs(verticalOffset) == appBarLayout?.totalScrollRange) {
                    // If collapsed, then do this
                    toolbar.visibility = View.VISIBLE
                    cvSelectedTrack.visibility = View.VISIBLE
                    txtTrackTitle.visibility = View.VISIBLE
                    txtTrackSpeaker.visibility = View.VISIBLE
//                    imgPlayButton.visibility = View.GONE
                    toolbar.alpha = 1F
                    cvSelectedTrack.alpha = 1F
                    txtTrackTitle.alpha = 1F
                    txtTrackSpeaker.alpha = 1F
                } else if (verticalOffset == 0) {
                    // If expanded, then do this
                    toolbar.visibility = View.GONE
                    cvSelectedTrack.visibility = View.GONE
                    txtTrackTitle.visibility = View.GONE
                    txtTrackSpeaker.visibility = View.GONE
//                    imgPlayButton.visibility = View.VISIBLE
//                    imgPlayButton.alpha = 1F
                } else {
                    // Somewhere in between
                    // Do according to your requirement
                    toolbar.visibility = View.VISIBLE
                    cvSelectedTrack.visibility = View.VISIBLE
                    txtTrackTitle.visibility = View.VISIBLE
                    txtTrackSpeaker.visibility = View.VISIBLE
//                    imgPlayButton.visibility = View.VISIBLE

                    var alphaVal = (abs(verticalOffset.toFloat()) / maxOffset!!.toFloat()) * 1
                    toolbar.alpha = alphaVal

//                    if ( alphaVal < 0.20 ) imgPlayButton.alpha = 1F
//                    else imgPlayButton.alpha = 1 - alphaVal
                }
            })
        }
    }

    private fun handlePageState(state: PageState) {
        binding.apply {
            when (state) {
                PageState.Loading -> startShimmer()
                PageState.Completed -> stopShimmer()
                PageState.Error -> stopShimmer()
            }
        }
    }

    private fun startShimmer() = binding.includeTracklist.shimmerPodcastItemLayout.startShimmer()

    private fun stopShimmer() {
        with(binding.includeTracklist) {
            shimmerPodcastItemLayout.stopShimmer()
            shimmerPodcastItemLayout.isVisible = false
            rvPodcast.isVisible = true
            setupPodcastRecyclerView()
            setUpPodcastPlayer()
            setUpPlayerControls()
        }
    }

    private fun convertSecondsToTime(secs: Int): String {

        var n = secs
        var hour = (n / 3600).toString()

        n %= 3600
        var minutes = (n / 60).toString()

        n %= 60
        var seconds = n.toString()

        if (hour.toInt() < 10) hour = "0$hour"
        if (minutes.toInt() < 10) minutes = "0$minutes"
        if (seconds.toInt() < 10) seconds = "0$seconds"

        if (hour == "00") return "$minutes:$seconds"
        else return "$hour:$minutes:$seconds"

    }
}