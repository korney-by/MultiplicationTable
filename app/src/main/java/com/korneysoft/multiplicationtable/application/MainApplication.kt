package com.korneysoft.multiplicationtable.application

import android.app.Application
import com.korneysoft.multiplicationtable.domain.usecases.statistic.LoadStatisticUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.LoadVoicePreferencesUseCase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    internal lateinit var loadVoicePreferencesUseCase: LoadVoicePreferencesUseCase
    @Inject
    internal lateinit var loadStatisticUseCase: LoadStatisticUseCase

    override fun onCreate() {
        super.onCreate()
        loadPreferences()
    }

    private fun loadPreferences() {
        loadVoicePreferencesUseCase()
        loadStatisticUseCase()
    }

}
