package com.horizon.horizify.ui.home.viewmodel

import android.content.Context
import com.horizon.horizify.R
import com.horizon.horizify.base.BaseViewModel
import com.horizon.horizify.base.SharedPreference
import com.horizon.horizify.extensions.save
import com.horizon.horizify.ui.home.model.HeaderCardModel
import com.horizon.horizify.utils.Constants.WEB_URL

class HomeViewModel(val context: Context) : BaseViewModel(), IHomeViewModel {

    private val prefs = SharedPreference(context)

    override fun getHeaderCardImages(): List<HeaderCardModel> {
        // temporary implementation for local fetching
        val cardList = arrayListOf<HeaderCardModel>()
        cardList.add(HeaderCardModel(R.drawable.header))
        cardList.add(HeaderCardModel(R.drawable.g12))
        cardList.add(HeaderCardModel(R.drawable.hoorayzone))
        return cardList
    }

    override fun saveWebUrl(url: String) {
        prefs.removeValue(WEB_URL)
        prefs.save(WEB_URL, url)
    }
}