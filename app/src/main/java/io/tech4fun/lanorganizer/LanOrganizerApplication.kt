package io.tech4fun.lanorganizer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.tech4fun.lanorganizer.data.AppContainer
import io.tech4fun.lanorganizer.data.DefaultAppContainer

@HiltAndroidApp
class LanOrganizerApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}