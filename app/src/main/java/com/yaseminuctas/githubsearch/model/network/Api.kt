package com.yaseminuctas.githubsearch.model.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.yaseminuctas.githubsearch.model.RepoNameResponse
import com.yaseminuctas.githubsearch.model.UserNameResponse
import retrofit2.http.GET
import com.yaseminuctas.githubsearch.util.Const
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

interface Api {

    @GET("search/users")
     fun getSearchUserName(
        @Query("q") sKey: String
    ) : Deferred<Response<UserNameResponse>>

    @GET("search/repositories")
     fun getSearchRepoName(
        @Query("q") sKey: String
    ) : Deferred<Response<RepoNameResponse>>


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