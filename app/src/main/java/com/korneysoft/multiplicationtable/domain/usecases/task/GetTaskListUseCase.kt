package com.korneysoft.multiplicationtable.domain.usecases.task

import com.korneysoft.multiplicationtable.domain.entities.StudyNumber
import com.korneysoft.multiplicationtable.domain.entities.Task
import com.korneysoft.multiplicationtable.domain.entities.TaskList
import javax.inject.Inject

class GetTaskListUseCase @Inject constructor() {
    fun execute(number: Int): List<Task> {
        return if (number == StudyNumber.ALL_TABLE) {
            TaskList.getStudyTasksAllTable()
        } else {
            TaskList.getStudyTasksOnNumber(number)
        }
    }
}