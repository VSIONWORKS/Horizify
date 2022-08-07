package com.horizon.horizify.ui.video.model

import kotlinx.serialization.Serializable

@Serializable
data class VideoPlaylistModel(
    val nextPageToken: String? = null,
    val prevPageToken: String? = null,
    val items: List<ItemInfo>? = null
) {
    data class ItemInfo(val id: String, val snippet: SnippetInfo)
    data class SnippetInfo(val title: String, val description: String, val thumbnails: Thumbnail)
    data class Thumbnail(val high: HighResolution, val maxres: MaxResolution)
    data class HighResolution(val url: String? = null)
    data class MaxResolution(val url: String? = null)

}