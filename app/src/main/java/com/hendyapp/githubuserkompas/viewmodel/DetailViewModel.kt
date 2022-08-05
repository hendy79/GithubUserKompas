package com.hendyapp.githubuserkompas.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hendyapp.githubuserkompas.model.GithubRepo
import com.hendyapp.githubuserkompas.model.GithubUser
import com.hendyapp.githubuserkompas.repo.GlobalRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val globalRepo: GlobalRepo): ViewModel() {
    val userLive = MutableLiveData<GithubUser>()
    val reposLive = MutableLiveData<List<GithubRepo>>()
    val errorLive = MutableLiveData<String>()

    fun getUserData(userUrl: String, repoUrl: String) = viewModelScope.launch {
        try {
            userLive.postValue(globalRepo.getUser(userUrl))
            reposLive.postValue(globalRepo.getUserRepos(repoUrl))
        } catch (e: Exception) {
            Log.e("detail_error", e.stackTraceToString())
            errorLive.postValue("Failed to get Online data, please try again!")
        }
    }
}