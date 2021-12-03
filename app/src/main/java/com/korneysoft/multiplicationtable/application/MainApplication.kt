package com.korneysoft.multiplicationtable.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

private const val TAG = "T7-MainApplication"

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate() {
        super.onCreate()
        appPreferences.load()
        //Log.d(TAG, "appPreferences.load() - $appPreferences")

    }
}
