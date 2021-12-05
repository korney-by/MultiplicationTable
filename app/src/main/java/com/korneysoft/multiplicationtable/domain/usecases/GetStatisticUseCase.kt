package com.korneysoft.multiplicationtable.domain.usecases

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.domain.entities.Task
import com.korneysoft.multiplicationtable.domain.entities.TaskList
import javax.inject.Inject

class GetStatisticUseCase  @Inject constructor(private val soundRepository: SoundRepository) {
     fun execute() {
     }

}
