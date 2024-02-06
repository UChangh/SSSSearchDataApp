package com.example.ssssearchdataapp.externaldatas

import com.example.ssssearchdataapp.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DataRequestURLs {
    private const val SEARCH_BASE_URL = "https://dapi.kakao.com/"

    private val kakaoRetrofit by lazy {     // Retrofit 인스턴스를 초기화하고 반환한다.
        val gson = GsonBuilder().setLenient().create()  // setLenient()는 JSON 파싱이 더 유연하게 처리되도록 함
        Retrofit.Builder()
            .baseUrl(SEARCH_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(gson)   // 컨버터 추가
            ).client(
                createOkHttpClient()
            ).build()
    }

    val kakaoNetwork: KakaoNetwork by lazy {    // API 서비스 객체 반환
        kakaoRetrofit.create(KakaoNetwork::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if(BuildConfig.DEBUG) interceptor.level = HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }
}