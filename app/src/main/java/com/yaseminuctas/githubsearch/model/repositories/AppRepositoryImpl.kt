package com.yaseminuctas.githubsearch.model.repositories

import com.yaseminuctas.githubsearch.model.RepoNameResponse
import com.yaseminuctas.githubsearch.model.UserNameResponse
import com.yaseminuctas.githubsearch.model.network.AppService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppRepositoryImpl(private val appService: AppService): AppRepository {

    override fun getSearchUserName(searchKey: String): Flow<UserNameResponse> = flow { emit(appService.getSearchUserName(searchKey)) }

    override fun getSearchRepoName(searchKey: String): Flow<RepoNameResponse> = flow { emit(appService.getSearchRepoName(searchKey)) }

}