package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.ui.player.PlayerHandler
import javax.inject.Inject

class PlayHelloUseCase @Inject constructor(private val soundRepository: SoundRepository) {
    fun execute() {
        val fileDescriptor =
            soundRepository.getSoundFileDescriptor(soundRepository.helloSoundFileId)
        fileDescriptor?.let {
            PlayerHandler.play(fileDescriptor, soundRepository.voiceSpeed)
        }
    }
}