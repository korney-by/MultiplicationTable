package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.application.AppPreferences
import javax.inject.Inject

class LoadVoicePreferencesUseCase @Inject constructor(
    private val appPreferences: AppPreferences
) {
    operator fun invoke() {
        appPreferences.loadSoundRepositorySettings()
    }
}
