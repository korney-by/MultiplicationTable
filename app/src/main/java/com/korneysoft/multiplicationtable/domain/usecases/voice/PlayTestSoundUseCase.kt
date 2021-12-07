package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import javax.inject.Inject

class PlayTestSoundUseCase @Inject constructor(
    private val soundRepository: SoundRepository,
    private val playSoundUseCase: PlaySoundUseCase
) {
    var duration = 0L
        private set

    fun execute() {
        playSoundUseCase.execute(soundRepository.testSoundFileId)
        duration = playSoundUseCase.duration
    }
}
