package com.yaseminuctas.githubsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaseminuctas.githubsearch.model.RepoNameResponse
import com.yaseminuctas.githubsearch.model.UserNameResponse
import com.yaseminuctas.githubsearch.model.network.Api
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val liveUserData: MutableLiveData<UserNameResponse> = MutableLiveData()
    private val liveRepoData: MutableLiveData<RepoNameResponse> = MutableLiveData()
    private val userList = MutableLiveData<List<UserNameResponse.ItemUserName>>()
    var userNameList = MutableLiveData<List<UserNameResponse.ItemUserName>>()
        get() = userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private var isLoadingRepo = false
        set (value: Boolean) {
            field = value
            checkIsLoading()
        }
    private var isLoadingUserName = false
        set (value: Boolean) {
            field = value
            checkIsLoading()
        }


    private val _userName = MutableLiveData<List<UserNameResponse.ItemUserName>>()
    val userName: LiveData<List<UserNameResponse.ItemUserName>>
        get() = _userName

    private val _repoName = MutableLiveData<List<RepoNameResponse.ItemRepoName>>()
    val repoName: LiveData<List<RepoNameResponse.ItemRepoName>>
        get() = _repoName

    private val dataList = ArrayList<UserNameResponse.ItemUserName>()
    private val dataRepoList = ArrayList<RepoNameResponse.ItemRepoName>()

    fun getSearchUserName(searchKey: String) {
        isLoadingUserName = true
        viewModelScope.launch {
            val service = Api.invoke()
            val response = service.getSearchUserName(searchKey).await()

            if (response.isSuccessful) {
                liveUserData.postValue(response.body())
                dataList.clear()
                for (i in response.body()?.items!!) {
                    dataList.add(i)
                    if (i == response.body()?.items!!.last()) {
                        isLoadingUserName = false
                        _userName.value = dataList
                    }

                }
            } else {
                isLoadingUserName = false
                removeData(true, false)
            }
        }
    }

    fun getSearchRepoName(searchKey: String) {
     isLoadingRepo = true
     viewModelScope.launch {
         val service = Api.invoke()
         val response = service.getSearchRepoName(searchKey).await()
         if (response.isSuccessful) {
             liveRepoData.postValue(response.body())
                dataRepoList.clear()
             for (i in response.body()?.items!!) {
                 dataRepoList.add(i)
                 if (i == response.body()?.items!!.last()) {
                     isLoadingRepo = false
                     _repoName.value = dataRepoList
                 }
             }
         } else {
             isLoadingRepo = false
             removeData(false, true)
         }
     }
    }

    private fun checkIsLoading() {
        val isLoading = isLoadingRepo && isLoadingUserName
        _isLoading.value = isLoading
    }

    fun removeData(isUserName: Boolean, isRepoName: Boolean) {
        if (isUserName) {
            val userList = ArrayList<UserNameResponse.ItemUserName>()
            _userName.value = userList
        }
        if (isRepoName) {
            val repoList = ArrayList<RepoNameResponse.ItemRepoName>()
            _repoName.value = repoList
        }
    }


}