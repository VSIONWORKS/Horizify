package com.horizon.horizify.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horizon.horizify.utils.PageState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    open val pageLoader = MutableStateFlow<PageState>(PageState.Loading)

    fun safeLaunch(dispatcher: CoroutineDispatcher, method: suspend () -> Unit) {
        viewModelScope.launch(errorHandler + dispatcher) {
            method.invoke()
        }
    }

    open fun onExceptionReceived(e: Throwable) {
        Log.e("Error : ", "$e")
        if (pageLoader.value == PageState.Loading) pageLoader.value = PageState.Completed
    }

    val errorHandler = CoroutineExceptionHandler { _, throwable ->
        onExceptionReceived(throwable)
        Log.e("Error : ", "$throwable")
    }
}