package com.horizon.horizify.service

import com.horizon.horizify.ui.video.model.VideoPlaylistModel
import com.horizon.horizify.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("youtube/v3/playlists")
    suspend fun getPlayLists(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("key") apiKey: String
    ): VideoPlaylistModel

    @GET("youtube/v3/playlists")
    suspend fun getPlayListNextPage(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("pageToken") pageToken: String,
        @Query("key") apiKey: String
    ): VideoPlaylistModel

    companion object {

        operator fun invoke(): NetworkService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(NetworkService::class.java)
        }
    }
}