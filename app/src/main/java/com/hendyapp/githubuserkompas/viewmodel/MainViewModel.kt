package com.hendyapp.githubuserkompas.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hendyapp.githubuserkompas.model.GithubUser
import com.hendyapp.githubuserkompas.repo.GlobalRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val globalRepo: GlobalRepo): ViewModel() {
    val usersLive = MutableLiveData<List<GithubUser>>()
    val errorLive = MutableLiveData<String>()

    fun getSearchUser(query: String) = viewModelScope.launch {
        val users = try {
            val tmp = globalRepo.getSearchUsers(query).users
            globalRepo.insertUsers(tmp)
            tmp
        } catch (e: Exception) {
            errorLive.postValue("Failed to get Online data, load using Local data...")
            globalRepo.getSearchLocalUsers(query)
        }
        usersLive.postValue(users)
    }
}