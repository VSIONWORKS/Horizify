package com.horizon.horizify.ui.admin.viewmodel

import com.horizon.horizify.base.BaseViewModel
import com.horizon.horizify.ui.home.model.HeaderCardModel

class AdminViewModel : BaseViewModel() {

    fun getBannerCarousels() : List<HeaderCardModel>{
        val cardList = arrayListOf<HeaderCardModel>()
        cardList.add(HeaderCardModel(isDefault = true))
        return cardList
    }
}