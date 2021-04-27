package com.yaseminuctas.githubsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaseminuctas.githubsearch.model.RepoNameResponse
import com.yaseminuctas.githubsearch.model.UserNameResponse
import com.yaseminuctas.githubsearch.model.repositories.AppRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel(private val appRepository: AppRepository) : ViewModel() {

    private val liveUserData: MutableLiveData<UserNameResponse> = MutableLiveData()
    private val liveRepoData: MutableLiveData<RepoNameResponse> = MutableLiveData()
    private val userList = MutableLiveData<List<UserNameResponse.ItemUserName>>()
    var userNameList = MutableLiveData<List<UserNameResponse.ItemUserName>>()
        get() = userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun <T> callService(
        service: Flow<T>,
        onSuccess: (T) -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            service.catch {
                onError()
            }.collect {
                onSuccess(it)
            }
        }
    }


    private var isLoadingRepo = false
        set(value: Boolean) {
            field = value
            checkIsLoading()
        }
    private var isLoadingUserName = false
        set(value: Boolean) {
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
        callService(appRepository.getSearchUserName(searchKey), {
            liveUserData.postValue(it)
            dataList.clear()
            for (i in it.items!!) {
                dataList.add(i)
                if (i == it.items!!.last()) {
                    isLoadingUserName = false
                    _userName.value = dataList
                }

            }
        }, {
            isLoadingUserName = false
            removeData(true, false)
        })
    }

    fun getSearchRepoName(searchKey: String) {
        isLoadingRepo = true
        callService(appRepository.getSearchRepoName(searchKey), {
            liveRepoData.postValue(it)
            dataRepoList.clear()
            for (i in it.items!!) {
                dataRepoList.add(i)
                if (i == it.items!!.last()) {
                    isLoadingRepo = false
                    _repoName.value = dataRepoList
                }
            }

        }, {
            isLoadingRepo = false
            removeData(false, true)
        })
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