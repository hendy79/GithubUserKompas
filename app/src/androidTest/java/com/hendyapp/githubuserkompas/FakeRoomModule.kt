package com.hendyapp.githubuserkompas

import android.content.Context
import androidx.room.Room
import com.hendyapp.githubuserkompas.hilt.RoomModule
import com.hendyapp.githubuserkompas.room.GithubDB
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RoomModule::class]
)
object FakeRoomModule {
    @Singleton
    @Provides
    fun provideRoomMovieDb(@ApplicationContext context: Context): GithubDB {
        return Room.inMemoryDatabaseBuilder(context, GithubDB::class.java)
            .build()
    }
}