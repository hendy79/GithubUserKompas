package com.hendyapp.githubuserkompas.repo

import com.hendyapp.githubuserkompas.model.GithubUser
import com.hendyapp.githubuserkompas.retrofit.GithubRest
import com.hendyapp.githubuserkompas.room.GithubDB

open class GlobalRepo(
    private val githubRest: GithubRest,
    private val githubDB: GithubDB
){
    open suspend fun getSearchUsers(query: String)
        = githubRest.getSearchUsers(query)

    open suspend fun getSearchLocalUsers(query: String)
        = githubDB.githubDao().getWhereIn(query)

    open suspend fun getUser(url: String)
        = githubRest.getUser(url)

    open suspend fun getUserRepos(url: String)
        = githubRest.getUserRepos(url)

    open suspend fun insertUsers(users: List<GithubUser>)
        = githubDB.githubDao().insertAll(users)
}