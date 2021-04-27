package com.yaseminuctas.githubsearch.model.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.yaseminuctas.githubsearch.model.RepoNameResponse
import com.yaseminuctas.githubsearch.model.UserNameResponse
import retrofit2.http.GET
import com.yaseminuctas.githubsearch.util.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

interface AppService {

    @GET("search/users")
     suspend fun getSearchUserName(
        @Query("q") sKey: String
    ) : UserNameResponse

    @GET("search/repositories")
     suspend fun getSearchRepoName(
        @Query("q") sKey: String
    ) : RepoNameResponse

}