package com.hendyapp.githubuserkompas.util

import android.util.Log
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object CountingIdlingResourceSingleton {

    private const val RESOURCE = "GLOBAL"

    @JvmField val countingIdlingResource = CountingIdlingResource(RESOURCE)

    val idlingResource: IdlingResource
        get() = countingIdlingResource

    fun increment() {
        Log.v("CountingIdling", "increment")
        countingIdlingResource.increment()
    }

    fun decrement() {
        Log.v("CountingIdling", "decrement")
        if(!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}