package com.horizon.horizify.commonModel

import com.horizon.horizify.ui.admin.AdminSetupEnum

data class BannerTypeModel(
    val type: AdminSetupEnum = AdminSetupEnum.BANNER,
    val banner: BannerModel = BannerModel()
)
