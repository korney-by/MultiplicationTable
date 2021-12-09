package com.korneysoft.multiplicationtable.domain.usecases.task

import com.korneysoft.multiplicationtable.domain.data.TaskList
import com.korneysoft.multiplicationtable.domain.entities.StudyNumber
import com.korneysoft.multiplicationtable.domain.entities.Task
import javax.inject.Inject

class GetTaskListUseCase @Inject constructor(
    private val taskList: TaskList
) {
    operator fun invoke(number: Int): List<Task> {
        return if (number == StudyNumber.ALL_TABLE) {
            taskList.getStudyAllUniqueTasks()
        } else {
            taskList.getStudyTasksOnNumber(number)
        }
    }
}
