package com.horizon.horizify.ui.podcast.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.PodcastPlayerItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class PodcastPlayerItem : BindableItem<PodcastPlayerItemBinding>() {
    override fun bind(viewBinding: PodcastPlayerItemBinding, position: Int) {
        with(viewBinding){
            // code here
        }
    }

    override fun getLayout(): Int = R.layout.podcast_player_item

    override fun initializeViewBinding(view: View): PodcastPlayerItemBinding = PodcastPlayerItemBinding.bind(view)
}