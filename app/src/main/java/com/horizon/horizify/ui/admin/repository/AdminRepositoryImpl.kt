package com.horizon.horizify.ui.admin.repository

import com.google.gson.Gson
import com.horizon.horizify.base.SharedPreference
import com.horizon.horizify.common.model.BannerTypeModel
import com.horizon.horizify.extensions.get
import com.horizon.horizify.extensions.saveObject
import com.horizon.horizify.repository.FirebaseRepository

class AdminRepositoryImpl(private val prefs: SharedPreference, private val firebaseRepository: FirebaseRepository) : AdminRepository, FirebaseRepository by firebaseRepository {

    override fun saveBanner(key: String, model: BannerTypeModel) {
        prefs.removeValue(key)
        prefs.saveObject(key, model)
    }

    override fun getBanner(key: String): BannerTypeModel {
        val gson = Gson()
        val jsonObject = prefs?.get(key, "") ?: ""
        return gson.fromJson(jsonObject, BannerTypeModel::class.java) ?: BannerTypeModel()
    }
}