package com.korneysoft.multiplicationtable.domain.usecases

import com.korneysoft.multiplicationtable.domain.entities.StudyNumbers
import com.korneysoft.multiplicationtable.domain.entities.Task
import com.korneysoft.multiplicationtable.domain.entities.TaskList
import javax.inject.Inject

class GetStudyListUseCase @Inject constructor() {

    fun execute(number: Int): List<Task> {
        return if (number == StudyNumbers.ALL_TABLE) {
            TaskList.getStudyTasksAllTable()
        } else {
            TaskList.getStudyTasksOnNumber(number)
        }
    }
}
