package com.horizon.horizify.ui.podcast.viewmodel

import com.horizon.horizify.base.BaseViewModel
import com.horizon.horizify.ui.podcast.model.PodcastModel
import com.horizon.horizify.ui.podcast.repository.PodcastRepository
import com.horizon.horizify.utils.PageState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow

class PodcastViewModel(private val repository: PodcastRepository) : BaseViewModel(), IPodcastViewModel {

    override val pageState = MutableStateFlow<PageState>(PageState.Loading)

    var trackListItemData: PodcastModel = PodcastModel()

    override fun setup() {
        safeLaunch(Dispatchers.IO) {
            getTrackList()
        }
    }

    override suspend fun getTrackList() {
        pageState.value = PageState.Loading
        trackListItemData = repository.getTrackList()
        pageState.value = PageState.Completed
    }
}