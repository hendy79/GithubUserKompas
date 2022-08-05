package com.hendyapp.githubuserkompas.hilt

import com.hendyapp.githubuserkompas.model.GithubUser
import com.hendyapp.githubuserkompas.repo.GlobalRepo
import com.hendyapp.githubuserkompas.retrofit.GithubRest
import com.hendyapp.githubuserkompas.room.GithubDB
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GlobalModule {
    @Singleton
    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Singleton
    @Provides
    fun providesMoshiJsonAdapterMovie(moshi: Moshi): JsonAdapter<GithubUser> {
        return moshi.adapter(GithubUser::class.java)
    }

    @Singleton
    @Provides
    fun providegGlobalRepo(githubRest: GithubRest, githubDB: GithubDB): GlobalRepo {
        return GlobalRepo(githubRest, githubDB)
    }
}