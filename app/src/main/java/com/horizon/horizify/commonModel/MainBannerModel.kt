package com.horizon.horizify.commonModel

data class MainBannerModel(
    val isDefault: Boolean = false,
    var primary: BannerModel = BannerModel(),
    var banners: ArrayList<BannerModel> = arrayListOf()
)
