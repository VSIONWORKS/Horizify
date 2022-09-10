package com.horizon.horizify.ui.home.viewmodel

import com.horizon.horizify.commonModel.BannerModel
import com.horizon.horizify.commonModel.MainBannerModel
import com.horizon.horizify.ui.home.model.HeaderCardModel
import kotlinx.coroutines.flow.StateFlow

interface IHomeViewModel {

    val bannerCarousel : StateFlow<MainBannerModel>

    fun getPrimaryBanner()
    fun getBannerCarousel(bannerModel: BannerModel)
    fun getHeaderCardImages(): List<HeaderCardModel>

    fun saveWebUrl(url: String)
}