package com.example.profileview

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize libraries, perform setup, etc.
    }
}