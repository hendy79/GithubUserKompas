package com.hendyapp.githubuserkompas.hilt

import android.content.Context
import android.os.Handler
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hendyapp.githubuserkompas.view.adapter.DetailAdapter
import com.hendyapp.githubuserkompas.view.adapter.MainAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @ActivityScoped
    @Provides
    fun providesLayoutManagerLinear(@ActivityContext context: Context): LinearLayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    @ActivityScoped
    @Provides
    fun providesDividerItemDecoration(@ActivityContext context: Context): DividerItemDecoration {
        return DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
    }

    @ActivityScoped
    @Provides
    fun providesHandler(@ActivityContext context: Context): Handler {
        return Handler(context.mainLooper)
    }

    @ActivityScoped
    @Provides
    fun providesMainAdapter(@ActivityContext context: Context): MainAdapter {
        return MainAdapter(context)
    }

    @ActivityScoped
    @Provides
    fun providesDetailAdapter(@ActivityContext context: Context): DetailAdapter {
        return DetailAdapter(context)
    }
}