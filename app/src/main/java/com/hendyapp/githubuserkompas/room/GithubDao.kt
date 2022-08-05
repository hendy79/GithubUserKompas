package com.hendyapp.githubuserkompas.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hendyapp.githubuserkompas.model.GithubUser
import com.hendyapp.githubuserkompas.util.MyConstants

@Dao
interface GithubDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<GithubUser>)

    @Query("SELECT * FROM ${MyConstants.ROOM_TABLE_USERS} WHERE login LIKE :query")
    suspend fun getWhereIn(query: String): List<GithubUser>
}