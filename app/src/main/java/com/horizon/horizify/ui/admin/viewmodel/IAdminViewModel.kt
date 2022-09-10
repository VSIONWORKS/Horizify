package com.horizon.horizify.ui.admin.viewmodel

import android.net.Uri
import com.horizon.horizify.commonModel.BannerModel
import com.horizon.horizify.commonModel.BannerTypeModel
import com.horizon.horizify.commonModel.ImageUriModel
import com.horizon.horizify.commonModel.MainBannerModel
import com.horizon.horizify.ui.admin.AdminSetupEnum
import com.horizon.horizify.utils.PageState
import kotlinx.coroutines.flow.StateFlow

interface IAdminViewModel {

    val imageUriModel: StateFlow<ImageUriModel>
    val bannerCarousel : StateFlow<MainBannerModel>
    val bannerTypeModel: StateFlow<BannerTypeModel>

    val pageState: StateFlow<PageState>
    val saveState: StateFlow<PageState>

    fun saveBanner(model: BannerTypeModel)
    fun setupBanner(): BannerTypeModel

    fun setNewImageUri(uri: Uri)

    fun save(type: AdminSetupEnum, banner: BannerModel)
    fun saveBannerToDatabase(type: AdminSetupEnum, banner: BannerModel)

    fun getBannerCarousel()
    fun getPrimaryBanner()
    fun getBanners()

    fun updateBanner(type: AdminSetupEnum, banner: BannerModel, postId: String)
    fun removeBanner(type: AdminSetupEnum, banner: BannerModel, postId: String)

    fun getCurrentDateTime(): String
}