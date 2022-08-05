package com.hendyapp.githubuserkompas.hilt

import android.content.Context
import androidx.room.Room
import com.hendyapp.githubuserkompas.room.GithubDB
import com.hendyapp.githubuserkompas.util.MyConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideRoomMovieDb(@ApplicationContext context: Context): GithubDB {
        return Room.databaseBuilder(context, GithubDB::class.java, MyConstants.ROOM_DB)
            .build()
    }
}