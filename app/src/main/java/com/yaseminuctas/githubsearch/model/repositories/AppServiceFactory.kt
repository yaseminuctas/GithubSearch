package com.yaseminuctas.githubsearch.model.repositories

import com.yaseminuctas.githubsearch.model.network.AppService
import com.yaseminuctas.githubsearch.util.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppServiceFactory {

    fun buildService(): AppService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Const.BASE_URL)
            .client(buildHttpClient())
            .build()
            .create(AppService::class.java)
    }

    private fun buildHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return okHttpClientBuilder.build()
    }
}