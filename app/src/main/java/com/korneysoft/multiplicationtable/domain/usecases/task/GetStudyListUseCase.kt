package com.korneysoft.multiplicationtable.domain.usecases.task

import com.korneysoft.multiplicationtable.domain.entities.StudyNumber
import com.korneysoft.multiplicationtable.domain.entities.Task
import javax.inject.Inject

class GetStudyListUseCase @Inject constructor(
    private val getTaskListUseCase: GetTaskListUseCase
) {

    fun execute(number: Int): List<Task> {
                return if (number == StudyNumber.ALL_TABLE) {
            getTaskListUseCase.execute(number).shuffled()
        } else {
            getTaskListUseCase.execute(number)
        }
    }
}
