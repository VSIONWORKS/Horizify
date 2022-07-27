package com.horizon.horizify.Extensions

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

inline fun Fragment.handleBackPress(crossinline event: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            event.invoke()
        }
    })
}