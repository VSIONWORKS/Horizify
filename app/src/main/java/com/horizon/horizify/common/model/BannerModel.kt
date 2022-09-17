package com.horizon.horizify.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BannerModel(
    val image: String = "",
    val title: String = "",
    val description: String = "",
    val linkCaption: String = "",
    val link: String = "",
    @SerialName("externalBrowser")
    var isExternalBrowser: Boolean = false,
    val date: String = "",
)