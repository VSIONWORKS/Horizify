package com.horizon.horizify.ui.admin.repository

import com.horizon.horizify.commonModel.BannerTypeModel
import com.horizon.horizify.repository.FirebaseRepository

interface AdminRepository: FirebaseRepository {
    fun saveBanner(key:String, model: BannerTypeModel)
    fun getBanner(key: String): BannerTypeModel
}