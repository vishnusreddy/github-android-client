package com.vishnusreddy.gpulls_android.utils.network

import com.vishnusreddy.gpulls_android.data.api.GitHubAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkTools {
    private const val BASE_URL = "https://api.github.com/"

    private val okhttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BASIC
                )
        ).build()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(okhttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    val githubAPI: GitHubAPI = getRetrofit().create(GitHubAPI::class.java)
}