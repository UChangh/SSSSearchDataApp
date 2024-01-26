package com.example.ssssearchdataapp.externaldatas

import com.example.ssssearchdataapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DataRequestURLs {
    private const val SEARCH_BASE_URL = "https://dapi.kakao.com/"

    private val kakaoRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SEARCH_BASE_URL).addConverterFactory(
                GsonConverterFactory.create()
            ).client(
                createOkHttpClient()
            ).build()
    }

    val kakaoNetwork: KakaoNetwork by lazy {
        kakaoRetrofit.create(KakaoNetwork::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if(BuildConfig.DEBUG) interceptor.level = HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(20,TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }
}