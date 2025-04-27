package com.currencyconverter.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CCApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
