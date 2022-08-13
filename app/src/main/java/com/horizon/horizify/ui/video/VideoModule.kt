package com.horizon.horizify.ui.video

import com.horizon.horizify.ui.video.item.VideoPlaylistItem
import com.horizon.horizify.ui.video.repository.VideoRepository
import com.horizon.horizify.ui.video.repository.VideoRepositoryImpl
import com.horizon.horizify.ui.video.viewmodel.VideoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object VideoModule {
    val dependency = module {
        factory<VideoRepository> { VideoRepositoryImpl(get(), get()) }
        factory { VideoPlaylistItem(it[0]) }
        viewModel { VideoViewModel(get()) }
    }
}