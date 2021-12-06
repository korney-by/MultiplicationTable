package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.player.PlayerHandler
import javax.inject.Inject

class GetVoicesUseCase @Inject constructor(private val soundRepository: SoundRepository){
    fun execute():List<String> {
        return soundRepository.voices
    }
}