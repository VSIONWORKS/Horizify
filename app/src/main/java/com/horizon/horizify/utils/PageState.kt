package com.horizon.horizify.utils

sealed class PageState {
    object Loading : PageState()
    object Error : PageState()
    object Completed : PageState()
}