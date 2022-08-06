package com.horizon.horizify.ui.video.viewmodel

import com.horizon.horizify.base.BaseViewModel
import com.horizon.horizify.ui.video.repository.VideoRepository
import kotlinx.coroutines.Dispatchers

class VideoViewModel(private val repository: VideoRepository) : BaseViewModel() {

    init {
        safeLaunch(Dispatchers.IO){
            val data = repository.getPlayList()
            val data2 = repository.getPlayListNextPage(data.nextPageToken!!)
        }
    }
}