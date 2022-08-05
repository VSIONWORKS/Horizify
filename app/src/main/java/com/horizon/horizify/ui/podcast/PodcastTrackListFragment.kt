package com.horizon.horizify.ui.podcast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.horizon.horizify.R
import com.horizon.horizify.databinding.PodcastTrackListItemBinding
import com.horizon.horizify.extensions.collectOnStart
import com.horizon.horizify.extensions.launchOnStart
import com.horizon.horizify.extensions.load
import com.horizon.horizify.ui.podcast.item.PodcastPlayerItem
import com.horizon.horizify.ui.podcast.viewmodel.PodcastViewModel
import com.horizon.horizify.utils.PageState
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class PodcastTrackListFragment : Fragment(R.layout.podcast_track_list_item) {

    private lateinit var binding: PodcastTrackListItemBinding

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    private val podcastViewModel : PodcastViewModel by viewModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PodcastTrackListItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startCollect()
        setUpAppBarEffect()
    }

    private fun startCollect() {
        with(podcastViewModel){
            pageState.collectOnStart(viewLifecycleOwner, ::handlePageState)
            launchOnStart { setup() }
        }
    }

    private fun setupPodcastRecyclerView() {
        val tracks = podcastViewModel.trackListItemData.trackList.map { PodcastPlayerItem(it) }
        with(binding){
            imgPodcastTrackCover.load(podcastViewModel.trackListItemData.podcastCover)
            includeTracklist.rvPodcast.apply {
                adapter = groupAdapter.apply {
                    addAll(tracks)
                }
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun setUpAppBarEffect() {
        with(binding){
            appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                var maxOffset = appBarLayout?.totalScrollRange
                if (abs(verticalOffset) == appBarLayout?.totalScrollRange) {
                    // If collapsed, then do this
                    toolbar.visibility = View.VISIBLE
                    cvSelectedTrack.visibility = View.VISIBLE
                    txtTrackTitle.visibility = View.VISIBLE
                    txtTrackSpeaker.visibility = View.VISIBLE
                    imgPlayButton.visibility = View.GONE
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
                    imgPlayButton.visibility = View.VISIBLE
                    imgPlayButton.alpha = 1F
                } else {
                    // Somewhere in between
                    // Do according to your requirement
                    toolbar.visibility = View.VISIBLE
                    cvSelectedTrack.visibility = View.VISIBLE
                    txtTrackTitle.visibility = View.VISIBLE
                    txtTrackSpeaker.visibility = View.VISIBLE
                    imgPlayButton.visibility = View.VISIBLE

                    var alphaVal = (abs(verticalOffset.toFloat()) / maxOffset!!.toFloat() ) * 1
                    cvSelectedTrack.alpha = alphaVal
                    txtTrackTitle.alpha = alphaVal
                    txtTrackSpeaker.alpha = alphaVal

                    if ( alphaVal < 0.20 ) imgPlayButton.alpha = 1F
                    else imgPlayButton.alpha = 1 - alphaVal
                }
            })
        }
    }

    fun handlePageState(state: PageState) {
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
        with(binding.includeTracklist){
            shimmerPodcastItemLayout.stopShimmer()
            shimmerPodcastItemLayout.isVisible = false
            rvPodcast.isVisible = true
            setupPodcastRecyclerView()
        }
    }
}