package com.horizon.horizify.utils

import android.os.Handler
import android.os.Looper
import java.util.*

object SingletonHandler {
    private val carouselHandler = Handler(Looper.getMainLooper())
    var runnable : Runnable? = null
    var timer : Timer? = null


    fun getCarouselHandler() : Handler {
        return carouselHandler
    }

    fun getCarouselTimer() : Timer? {
        timer = Timer()
        return timer
    }

    fun clearCarouselHandler() {
        if (runnable != null) carouselHandler.removeCallbacks(runnable!!)
        carouselHandler.removeCallbacksAndMessages(null)
        cancelTimer()
    }

    private fun cancelTimer() {
        timer?.cancel()
        timer?.purge()
    }
}