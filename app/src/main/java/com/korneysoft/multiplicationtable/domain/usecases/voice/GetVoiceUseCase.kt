package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import javax.inject.Inject

class GetVoiceUseCase @Inject constructor(private val soundRepository: SoundRepository){
    fun execute():String {
        return soundRepository.voiceName
    }
}