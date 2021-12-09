package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import javax.inject.Inject

class GetDefaultVoiceUseCase  @Inject constructor(private val soundRepository: SoundRepository){
    operator fun invoke():String {
        return soundRepository.defaultVoice
    }
}
