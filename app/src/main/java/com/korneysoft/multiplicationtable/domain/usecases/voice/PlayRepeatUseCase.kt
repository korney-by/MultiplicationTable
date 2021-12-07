package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.player.PlayerHandler
import javax.inject.Inject

class PlayRepeatUseCase @Inject constructor(private val soundRepository: SoundRepository) {

    var duration = 0L
        private set

    fun execute() {
        val fileDescriptor =
            soundRepository.getSoundFileDescriptor(soundRepository.repeatSoundFileId)
        fileDescriptor?.let {
            PlayerHandler.play(fileDescriptor, soundRepository.voiceSpeed)
            duration = PlayerHandler.duration
        }
    }
}
