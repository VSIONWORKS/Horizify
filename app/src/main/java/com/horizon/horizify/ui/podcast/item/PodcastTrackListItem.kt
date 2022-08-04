package com.horizon.horizify.ui.podcast.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.PodcastTrackListItemBinding
import com.horizon.horizify.ui.podcast.viewmodel.PodcastViewModel
import com.xwray.groupie.viewbinding.BindableItem

class PodcastTrackListItem(viewModel : PodcastViewModel) : BindableItem<PodcastTrackListItemBinding>() {
    override fun bind(viewBinding: PodcastTrackListItemBinding, position: Int) {
        with(viewBinding){
            // code here
            toolbarLayout.title = "Listen to Podcast"
        }
    }

    override fun getLayout(): Int = R.layout.podcast_track_list_item

    override fun initializeViewBinding(view: View): PodcastTrackListItemBinding = PodcastTrackListItemBinding.bind(view)
}