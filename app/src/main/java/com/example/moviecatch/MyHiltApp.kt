package com.example.moviecatch

import dagger.hilt.android.HiltAndroidApp
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication
import timber.log.Timber

@HiltAndroidApp
class MyHiltApp : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}