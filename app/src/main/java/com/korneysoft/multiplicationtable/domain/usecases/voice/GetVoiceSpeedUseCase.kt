package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import javax.inject.Inject

class GetVoiceSpeedUseCase @Inject constructor(private val soundRepository: SoundRepository){
    fun execute():Int {
        return soundRepository.voiceSpeed
    }
}