package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.ui.player.PlayerHandler
import javax.inject.Inject

class PlayErrorUseCase @Inject constructor(private val soundRepository: SoundRepository) {
    operator fun invoke() {
        val fileDescriptor =
            soundRepository.getSoundFileDescriptor(soundRepository.errorSoundFileId)
        fileDescriptor?.let {
            PlayerHandler.play(fileDescriptor, soundRepository.voiceSpeed)
        }
    }
}
