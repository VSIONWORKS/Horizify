package com.horizon.horizify.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlin.reflect.KMutableProperty0

/**
 * Launches collect when [LifecycleCoroutineScope] is at least in [Lifecycle.State.STARTED] state.
 * @param kMutableProperty0 - accepts any var equal to [T] and is set during [collect]
 * */
suspend infix fun <T> Flow<T>.flowTo(stateFlow: MutableStateFlow<T>) {
    collect {
        stateFlow.value = it
    }
}

/**
 * Launches collect when [LifecycleCoroutineScope] is at least in [Lifecycle.State.STARTED] state.
 * @param kMutableProperty0 - accepts any var equal to [T] and is set during [collect]
 * */
fun <T> Flow<T>.collectOnStart(lifecycleOwner: LifecycleOwner, kMutableProperty0: KMutableProperty0<T>) {
    lifecycleOwner.launchOnStart {
        collect {
            kMutableProperty0.set(it)
        }
    }
}

/**
 * Launches collect when [LifecycleCoroutineScope] is at least in [Lifecycle.State.STARTED] state.
 * @param subscriber - accepts any method accepting [T] as parameter
 * */
fun <T> Flow<T>.collectOnStart(lifecycleOwner: LifecycleOwner, subscriber: suspend (T) -> Unit) {
    lifecycleOwner.launchOnStart {
        collect {
            subscriber.invoke(it)
        }
    }
}