package com.horizon.horizify.ui.video.model


import kotlinx.serialization.Serializable

@Serializable
data class VideoPlaylistItemModel(
    val nextPageToken: String? = null,
    val items: List<Video>? = null
) {
    data class Video(val snippet: Snippet)
    data class Snippet(val title: String, val description: String, val thumbnails: Thumbnail, val publishedAt: String, val resourceId: ResourceId)
    data class Thumbnail(val high: HighResolution, val maxres: MaxResolution)
    data class HighResolution(val url: String? = null)
    data class MaxResolution(val url: String? = null)
    data class ResourceId(val videoId: String)
}