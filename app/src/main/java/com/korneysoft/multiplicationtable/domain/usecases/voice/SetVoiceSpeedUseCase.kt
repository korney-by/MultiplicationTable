package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import javax.inject.Inject

class SetVoiceSpeedUseCase  @Inject constructor(private val soundRepository: SoundRepository){
    fun execute(voiceSpeed: Int) {
        return soundRepository.setVoiceSpeed(voiceSpeed)
    }
}