package com.horizon.horizify.common.model

data class MainBannerModel(
    val isDefault: Boolean = false,
    var primary: BannerModel = BannerModel(),
    var banners: ArrayList<BannerModel> = arrayListOf()
)
