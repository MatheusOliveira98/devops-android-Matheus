package com.example.devops_matheus

import android.app.Application
import timber.log.Timber

class DevopsMatheus: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}