package com.korneysoft.multiplicationtable.application

import android.app.Application
import com.korneysoft.multiplicationtable.domain.usecases.properties.LoadStatisticUseCase
import com.korneysoft.multiplicationtable.domain.usecases.properties.LoadVoicePropertiesUseCase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

private const val TAG = "T7-MainApplication"

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var loadVoicePropertiesUseCase: LoadVoicePropertiesUseCase
    @Inject
    lateinit var loadStatisticUseCase: LoadStatisticUseCase

    override fun onCreate() {
        super.onCreate()
        loadVoicePropertiesUseCase()
        loadStatisticUseCase()
        //Log.d(TAG, "appPreferences.load() - $appPreferences")
    }
}
