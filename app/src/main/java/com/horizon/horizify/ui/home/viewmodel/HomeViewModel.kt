package com.horizon.horizify.ui.home.viewmodel

import android.os.Handler
import android.os.Looper
import com.horizon.horizify.R
import com.horizon.horizify.base.BaseViewModel
import com.horizon.horizify.ui.home.model.HeaderCardModel
import java.util.*

class HomeViewModel : BaseViewModel(), IHomeViewModel {

    private val handler = Handler(Looper.getMainLooper())
    private var runnable : Runnable? = null
    private var timer = Timer()

    override fun getHeaderCardImages(): List<HeaderCardModel> {
        // temporary implementation for local fetching
        val cardList = arrayListOf<HeaderCardModel>()
        cardList.add(HeaderCardModel(R.drawable.header))
        cardList.add(HeaderCardModel(R.drawable.sample2))
        cardList.add(HeaderCardModel(R.drawable.sample5))
        return cardList
    }

    fun getHandler() : Handler = handler
    fun getRunnable() : Runnable? = runnable
    fun getTimer() : Timer = timer

    fun clearHandler() {
        if (runnable != null) handler.removeCallbacks(runnable!!)
        handler.removeCallbacksAndMessages(null)
        timer.cancel()
        timer.purge()
    }
}