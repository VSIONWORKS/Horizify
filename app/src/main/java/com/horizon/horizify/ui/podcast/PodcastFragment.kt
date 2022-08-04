package com.horizon.horizify.ui.podcast

import android.os.Bundle
import android.view.View
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.extensions.setBody
import com.horizon.horizify.ui.podcast.item.PodcastTrackListItem
import com.horizon.horizify.ui.podcast.viewmodel.PodcastViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class PodcastFragment : GroupieFragment() {

    private val vm : PodcastViewModel by viewModel()

    private val podcastTrackListItem by inject<PodcastTrackListItem>{ parametersOf(vm)}

    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {
        with(root){
            setBody(podcastTrackListItem)
        }
    }
}