package com.horizon.horizify.ui.podcast

import com.horizon.horizify.ui.podcast.item.PodcastTrackListItem
import com.horizon.horizify.ui.podcast.viewmodel.PodcastViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PodcastModule {
    val dependency = module {
        factory { PodcastTrackListItem(it[0]) }
        viewModel { PodcastViewModel() }
    }
}