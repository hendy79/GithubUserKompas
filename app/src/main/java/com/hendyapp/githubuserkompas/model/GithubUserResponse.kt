package com.hendyapp.githubuserkompas.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubUserResponse(
    @Json(name="total_count")
    val total: Long,
    @Json(name="items")
    val users: List<GithubUser>
)
