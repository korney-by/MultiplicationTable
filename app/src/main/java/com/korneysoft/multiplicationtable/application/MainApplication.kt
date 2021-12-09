package com.korneysoft.multiplicationtable.application

import android.app.Application
import com.korneysoft.multiplicationtable.domain.usecases.statistic.LoadStatisticUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.LoadVoicePreferencesUseCase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

private const val TAG = "T7-MainApplication"

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var loadVoicePreferencesUseCase: LoadVoicePreferencesUseCase
    @Inject
    lateinit var loadStatisticUseCase: LoadStatisticUseCase

    override fun onCreate() {
        super.onCreate()
        loadVoicePreferencesUseCase()
        loadStatisticUseCase()
        //Log.d(TAG, "appPreferences.load() - $appPreferences")
    }
}
