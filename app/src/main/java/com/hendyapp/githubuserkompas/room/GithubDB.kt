package com.hendyapp.githubuserkompas.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hendyapp.githubuserkompas.model.GithubUser

@Database(version = 1, entities = [GithubUser::class], exportSchema = false)
abstract class GithubDB: RoomDatabase() {
    abstract fun githubDao(): GithubDao
}