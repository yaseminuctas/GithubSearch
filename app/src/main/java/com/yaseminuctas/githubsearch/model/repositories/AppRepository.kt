package com.yaseminuctas.githubsearch.model.repositories

import com.yaseminuctas.githubsearch.model.RepoNameResponse
import com.yaseminuctas.githubsearch.model.UserNameResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AppRepository {

    fun getSearchUserName(searchKey: String): Flow<UserNameResponse>

    fun getSearchRepoName(searchKey: String): Flow<RepoNameResponse>

}