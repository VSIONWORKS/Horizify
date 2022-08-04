package com.horizon.horizify.ui.podcast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.horizon.horizify.R
import com.horizon.horizify.databinding.PodcastTrackListItemBinding

class PodcastTrackListFragment : Fragment(R.layout.podcast_track_list_item) {

    private lateinit var binding: PodcastTrackListItemBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PodcastTrackListItemBinding.inflate(inflater, container, false)
        return binding.root
    }
}