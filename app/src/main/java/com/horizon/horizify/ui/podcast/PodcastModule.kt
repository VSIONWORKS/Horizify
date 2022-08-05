package com.horizon.horizify.ui.podcast

import com.horizon.horizify.ui.podcast.item.PodcastTrackListItem
import com.horizon.horizify.ui.podcast.repository.PodcastRepository
import com.horizon.horizify.ui.podcast.repository.PodcastRepositoryImpl
import com.horizon.horizify.ui.podcast.viewmodel.PodcastViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PodcastModule {
    val dependency = module {
        factory <PodcastRepository>{ PodcastRepositoryImpl() }
        factory { PodcastTrackListItem(it[0]) }
        viewModel { PodcastViewModel(get()) }
    }
}