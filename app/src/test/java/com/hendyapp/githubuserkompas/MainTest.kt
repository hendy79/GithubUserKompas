package com.hendyapp.githubuserkompas

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hendyapp.githubuserkompas.model.GithubUser
import com.hendyapp.githubuserkompas.model.GithubUserResponse
import com.hendyapp.githubuserkompas.repo.GlobalRepo
import com.hendyapp.githubuserkompas.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class MainTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var globalRepo: GlobalRepo

    @Mock
    lateinit var observerUser: Observer<List<GithubUser>>

    @Mock
    lateinit var observerMsg: Observer<String>

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testApiCall_whenSuccess() {
        runTest {
            doReturn(GithubUserResponse(1, listOf(GithubUser(1, "", "", "", "", "", "")))).`when`(globalRepo).getSearchUsers("Test")
            doReturn(Unit).`when`(globalRepo).insertUsers(listOf(GithubUser(1, "", "", "", "", "", "")))
            val viewModel = MainViewModel(globalRepo)
            viewModel.getSearchUser("Test")
            viewModel.usersLive.observeForever(observerUser)
            verify(globalRepo).getSearchUsers("Test")
            verify(globalRepo).insertUsers(listOf(GithubUser(1, "", "", "", "", "", "")))
            verify(observerUser).onChanged(listOf(GithubUser(1, "", "", "", "", "", "")))
            viewModel.usersLive.removeObserver(observerUser)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testApiCall_whenError() {
        runTest {
            doReturn(RuntimeException()).`when`(globalRepo).getSearchUsers("Test")
            doReturn(listOf(GithubUser(1, "", "", "", "", "", ""))).`when`(globalRepo).getSearchLocalUsers("Test")
            val viewModel = MainViewModel(globalRepo)
            viewModel.getSearchUser("Test")
            viewModel.usersLive.observeForever(observerUser)
            viewModel.errorLive.observeForever(observerMsg)
            verify(globalRepo).getSearchUsers("Test")
            verify(observerMsg).onChanged("Failed to get Online data, load using Local data...")
            verify(globalRepo).getSearchLocalUsers("Test")
            verify(observerUser).onChanged(listOf(GithubUser(1, "", "", "", "", "", "")))
            viewModel.errorLive.removeObserver(observerMsg)
            viewModel.usersLive.removeObserver(observerUser)
        }
    }
}