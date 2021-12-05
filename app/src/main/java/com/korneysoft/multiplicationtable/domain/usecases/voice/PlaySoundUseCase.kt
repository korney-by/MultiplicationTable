package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.player.PlayerHandler
import javax.inject.Inject

class PlaySoundUseCase @Inject constructor(private val soundRepository: SoundRepository) {

    fun execute(taskId: String) {
        val fileDescriptor = soundRepository.getSoundFileDescriptor(taskId)
        fileDescriptor?.let {
            PlayerHandler.play(fileDescriptor,soundRepository.voiceSpeed)
        }
    }
}
