package com.horizon.horizify.ui.podcast.viewmodel

import com.horizon.horizify.utils.PageState
import kotlinx.coroutines.flow.StateFlow

interface IPodcastViewModel {

    val pageState: StateFlow<PageState>

    fun setup()
    suspend fun getTrackList()
}