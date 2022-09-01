package com.horizon.horizify.ui.admin.viewmodel

import android.net.Uri
import com.horizon.horizify.commonModel.BannerModel
import com.horizon.horizify.commonModel.CarouselModel
import com.horizon.horizify.commonModel.ImageUriModel
import com.horizon.horizify.ui.admin.AdminSetupEnum
import com.horizon.horizify.ui.home.model.HeaderCardModel
import com.horizon.horizify.utils.PageState
import kotlinx.coroutines.flow.StateFlow

interface IAdminViewModel {

    val imageUriModel: StateFlow<ImageUriModel>
    val primaryBanner : StateFlow<BannerModel>
    val carousel: StateFlow<CarouselModel>

    val pageState: StateFlow<PageState>

    fun getBannerCarousels(): List<HeaderCardModel>
    fun setNewImageUri(uri: Uri)

    fun saveBanner(type: AdminSetupEnum, banner: BannerModel)
    fun saveBannerToDatabase(type: AdminSetupEnum, banner: BannerModel)

    fun loadBanners()
    fun getPrimaryBanner()
    fun getCarousel()
}