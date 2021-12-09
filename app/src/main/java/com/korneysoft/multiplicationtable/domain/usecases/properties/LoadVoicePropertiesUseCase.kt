package com.korneysoft.multiplicationtable.domain.usecases.properties

import com.korneysoft.multiplicationtable.application.AppPreferences
import com.korneysoft.multiplicationtable.domain.data.RatingRepository
import javax.inject.Inject

class LoadVoicePropertiesUseCase @Inject constructor(
    private val appPreferences: AppPreferences
) {
    operator fun invoke() {
        appPreferences.loadSoundRepositorySettings()
    }
}
