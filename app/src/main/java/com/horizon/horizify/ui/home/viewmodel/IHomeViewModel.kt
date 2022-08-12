package com.horizon.horizify.ui.home.viewmodel

import com.horizon.horizify.ui.home.model.HeaderCardModel

interface IHomeViewModel {
    fun getHeaderCardImages(): List<HeaderCardModel>
}