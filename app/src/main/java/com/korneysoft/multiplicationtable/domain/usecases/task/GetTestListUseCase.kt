package com.korneysoft.multiplicationtable.domain.usecases.task

import com.korneysoft.multiplicationtable.domain.entities.Task
import javax.inject.Inject

class GetTestListUseCase @Inject constructor(
    private val getTaskListUseCase: GetTaskListUseCase
) {

    fun execute(number: Int): List<Task> {
        return getTaskListUseCase.execute(number).shuffled()
    }
}
