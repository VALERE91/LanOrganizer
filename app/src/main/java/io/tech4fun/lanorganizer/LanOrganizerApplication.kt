package io.tech4fun.lanorganizer

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import io.tech4fun.lanorganizer.data.AppDatabase
import io.tech4fun.lanorganizer.data.DefaultAppContainer

@HiltAndroidApp
class LanOrganizerApplication : Application() {
    val database : AppDatabase by lazy { AppDatabase.getDatabase(this) }
    companion object {
        private var sApplication: Application? = null

        fun getApplication(): Application? {
            return sApplication
        }

        fun getContext(): Context? {
            return getApplication()!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        sApplication = this
    }
}