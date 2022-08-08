package com.horizon.horizify.ui.video.model


import kotlinx.serialization.Serializable

@Serializable
class VideoPlaylistItemModel(
    val nextPageToken: String? = null,
    val items: List<Video>? = null
) {
    class Video(val snippet: Snippet)
    class Snippet(val title: String, val description: String, val thumbnails: Thumbnail, val publishedAt: String, val resourceId: ResourceId)
    class Thumbnail(val high: HighResolution, val maxres: MaxResolution)
    data class HighResolution(val url: String? = null)
    data class MaxResolution(val url: String? = null)
    class ResourceId(val videoId: String)
}