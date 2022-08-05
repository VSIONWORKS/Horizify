package com.horizon.horizify.ui.podcast.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.PodcastPlayerItemBinding
import com.horizon.horizify.extensions.load
import com.horizon.horizify.ui.podcast.model.PodcastTrackModel
import com.xwray.groupie.viewbinding.BindableItem

class PodcastPlayerItem(val track : PodcastTrackModel) : BindableItem<PodcastPlayerItemBinding>() {
    override fun bind(viewBinding: PodcastPlayerItemBinding, position: Int) {
        with(viewBinding){
            imgTrack.load(track.cover)
            txtTitle.text = track.title
            txtSpeaker.text = track.pastor
        }
    }

    override fun getLayout(): Int = R.layout.podcast_player_item

    override fun initializeViewBinding(view: View): PodcastPlayerItemBinding = PodcastPlayerItemBinding.bind(view)
}