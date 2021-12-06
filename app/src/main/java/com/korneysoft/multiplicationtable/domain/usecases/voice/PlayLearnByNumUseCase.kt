package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.player.PlayerHandler
import javax.inject.Inject

class PlayLearnByNumUseCase @Inject constructor(private val soundRepository: SoundRepository) {
    fun execute(number: Int) {
        val fileDescriptor =
            soundRepository.getSoundFileDescriptor(soundRepository.getLearnBySoundFileId(number))
        fileDescriptor?.let {
            PlayerHandler.play(fileDescriptor, soundRepository.voiceSpeed)
        }
    }
}