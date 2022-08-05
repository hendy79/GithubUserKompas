package com.hendyapp.githubuserkompas.retrofit

import com.hendyapp.githubuserkompas.model.GithubRepo
import com.hendyapp.githubuserkompas.model.GithubUser
import com.hendyapp.githubuserkompas.model.GithubUserResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GithubRest {
    @GET("search/users")
    suspend fun getSearchUsers(
        @Query("q") query: String
    ): GithubUserResponse

    @GET
    suspend fun getUser(
        @Url url: String
    ): GithubUser

    @GET
    suspend fun getUserRepos(
        @Url url: String
    ): List<GithubRepo>
}