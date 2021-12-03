package com.korneysoft.multiplicationtable.domain.usecases

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.player.PlayerHandler

class PlaySoundUserCase(private val soundRepository: SoundRepository) {

    fun execute(taskId: String) {
        val fileDescriptor = soundRepository.getSoundFileDescriptor(taskId)
        fileDescriptor?.let {
            PlayerHandler.play(fileDescriptor,soundRepository.voiceSpeed)
        }
    }
}
