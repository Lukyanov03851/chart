package com.lukyanov.chart.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChartApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}