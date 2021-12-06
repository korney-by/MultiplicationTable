package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import javax.inject.Inject

class GetVoiceSpeedMinUseCase  @Inject constructor(private val soundRepository: SoundRepository){
    fun execute():Int {
        return soundRepository.VOICE_SPEED_MIN
    }
}