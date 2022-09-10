package com.horizon.horizify.ui.home.model

import com.horizon.horizify.commonModel.BannerModel

data class HeaderCardModel(
    val isDefault: Boolean = false,
    val drawableId: Int = -1, // to removed
    val banner: BannerModel = BannerModel()
)