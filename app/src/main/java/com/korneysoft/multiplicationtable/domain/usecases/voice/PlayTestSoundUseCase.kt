package com.korneysoft.multiplicationtable.domain.usecases.voice

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import javax.inject.Inject

class PlayTestSoundUseCase @Inject constructor(private val soundRepository: SoundRepository) {
    @Inject
    lateinit var playSoundUseCase: PlaySoundUseCase

    fun execute() {
        playSoundUseCase.execute(soundRepository.testSoundFileId)
    }
}