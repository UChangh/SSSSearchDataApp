package com.example.ssssearchdataapp.externaldatas

import com.example.ssssearchdataapp.externaldatas.models.images.ImageDataGroup
import com.example.ssssearchdataapp.externaldatas.models.videos.VideoDataGroup
import com.example.ssssearchdataapp.objects.KakaoAPIKey.REST_API_KEY
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoNetwork {
    @GET("v2/search/image")
    suspend fun getItem(
        @Header("Authorization") apiKey:String = REST_API_KEY,
        @Query("query") query : String,
        @Query("size") size : Int
        ): ImageDataGroup

    @GET("v2/search/vclip")
    suspend fun getVideo(
        @Header("Authorization") apiKey: String = REST_API_KEY,
        @Query("query") query : String,
        @Query("size") size : Int
    ): VideoDataGroup
}