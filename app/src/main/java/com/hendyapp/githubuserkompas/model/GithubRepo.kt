package com.hendyapp.githubuserkompas.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubRepo(
    @Json(name="id")
    val id: Long,
    @Json(name="name")
    val name: String,
    @Json(name="description")
    val desc: String?,
    @Json(name="stargazers_count")
    val star: Int,
    @Json(name="updated_at")
    val updated: String
)