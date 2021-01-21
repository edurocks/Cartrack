package com.example.tekever.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CartrackApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}