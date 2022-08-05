package com.horizon.horizify.ui.podcast.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.PodcastPlayerItemBinding
import com.horizon.horizify.extensions.load
import com.horizon.horizify.ui.podcast.model.PodcastTrackModel
import com.horizon.horizify.utils.ItemActionWithPositionAndValue
import com.xwray.groupie.viewbinding.BindableItem

class PodcastPlayerItem(
    private val track : PodcastTrackModel,
    private val onClickTrackItem: ItemActionWithPositionAndValue<PodcastTrackModel>
) : BindableItem<PodcastPlayerItemBinding>() {

    override fun bind(viewBinding: PodcastPlayerItemBinding, position: Int) {
        with(viewBinding){
            imgTrack.load(track.cover)
            txtTitle.text = track.title
            txtSpeaker.text = track.pastor
            layoutPodcastItem.setOnClickListener {
                onClickTrackItem.actionCallback(position,track)
            }
        }
    }

    override fun getLayout(): Int = R.layout.podcast_player_item

    override fun initializeViewBinding(view: View): PodcastPlayerItemBinding = PodcastPlayerItemBinding.bind(view)
}