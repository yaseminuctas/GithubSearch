package com.yaseminuctas.githubsearch.model.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.yaseminuctas.githubsearch.model.UserNameResponse
import retrofit2.http.GET
import com.yaseminuctas.mvvm.util.Const
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface Api {

    @GET("search/users?q=username")
    fun getData(): Deferred<Response<UserNameResponse>>

    companion object {
        operator fun invoke(): Api {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(Const.BASE_URL)
                .build()
                .create(Api::class.java)
        }
    }
}