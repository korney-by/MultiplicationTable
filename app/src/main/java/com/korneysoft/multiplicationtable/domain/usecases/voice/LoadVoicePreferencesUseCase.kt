package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.application.AppPreferences
import com.korneysoft.multiplicationtable.domain.data.RatingRepository
import javax.inject.Inject

class LoadVoicePreferencesUseCase @Inject constructor(
    private val appPreferences: AppPreferences
) {
    operator fun invoke() {
        appPreferences.loadSoundRepositorySettings()
    }
}
