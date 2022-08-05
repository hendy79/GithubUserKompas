package com.hendyapp.githubuserkompas.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hendyapp.githubuserkompas.util.MyConstants
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = MyConstants.ROOM_TABLE_USERS)
@JsonClass(generateAdapter = true)
data class GithubUser(
    @PrimaryKey
    @Json(name="id")
    val id: Long,
    @Json(name="login")
    val login: String,
    @Json(name="avatar_url")
    val avatar: String,
    @Json(name="url")
    val profile: String,
    @Json(name="repos_url")
    val repos: String,
    @Json(name="name")
    var name: String?,
    @Json(name="bio")
    var bio: String?,
)
