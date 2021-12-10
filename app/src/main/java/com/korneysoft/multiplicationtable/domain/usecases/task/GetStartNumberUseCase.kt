package com.korneysoft.multiplicationtable.domain.usecases.task

import com.korneysoft.multiplicationtable.domain.data.TaskList
import javax.inject.Inject

class GetStartNumberUseCase @Inject constructor(
    private val taskList: TaskList
) {
    operator fun invoke(): Int {
        return taskList.startNumber
    }
}
