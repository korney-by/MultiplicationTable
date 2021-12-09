package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.ui.player.PlayerHandler
import javax.inject.Inject

class PlaySoundUseCase @Inject constructor(private val soundRepository: SoundRepository) {
    var duration = 0L
        private set

    fun execute(taskId: String) {
        val fileDescriptor = soundRepository.getSoundFileDescriptor(taskId)
        fileDescriptor?.let {
            PlayerHandler.play(fileDescriptor, soundRepository.voiceSpeed)
            duration = PlayerHandler.duration
        }
    }
}
