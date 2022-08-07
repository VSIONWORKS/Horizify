package com.horizon.horizify.ui.video.viewmodel

import com.horizon.horizify.ui.video.State
import kotlinx.coroutines.flow.StateFlow

interface IVideoViewModel {

    val pageState: StateFlow<State>

    fun setup()
}