package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import javax.inject.Inject

class GetVoiceSpeedDefaultUseCase @Inject constructor(
    private val soundRepository: SoundRepository
) {
    operator fun invoke(): Int {
        return soundRepository.voiceSpeedDefault
    }
}
