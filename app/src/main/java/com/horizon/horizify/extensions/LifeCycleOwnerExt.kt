package com.horizon.horizify.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope

/**
 * Launches subscriber when [LifecycleCoroutineScope] is at least in [Lifecycle.State.STARTED] state.
 * */
fun LifecycleOwner.launchOnStart(subscriber: suspend () -> Unit) {
    lifecycleScope.launchWhenStarted {
        subscriber.invoke()
    }
}