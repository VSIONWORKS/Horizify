package com.horizon.horizify.ui.home.viewmodel

import com.horizon.horizify.common.model.BannerModel
import com.horizon.horizify.common.model.MainBannerModel
import com.horizon.horizify.ui.home.model.HeaderCardModel
import com.horizon.horizify.utils.PageState
import kotlinx.coroutines.flow.StateFlow

interface IHomeViewModel {

    val selectedBanner: StateFlow<BannerModel>
    val bannerCarousel: StateFlow<MainBannerModel>
    val pageState: StateFlow<PageState>

    fun setSelectedBanner(banner: BannerModel)
    fun getPrimaryBanner()
    fun getBannerCarousel(bannerModel: BannerModel)
    fun getHeaderCardImages(): List<HeaderCardModel>

    fun saveWebUrl(url: String)
}