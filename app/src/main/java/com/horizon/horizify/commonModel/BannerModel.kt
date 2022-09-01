package com.horizon.horizify.commonModel

import kotlinx.serialization.Serializable

@Serializable
data class BannerModel(
    val image: String = "",
    val title: String = "",
    val description: String = "",
    val linkCaption: String = "",
    val link: String = "",
    val date: String = ""
)