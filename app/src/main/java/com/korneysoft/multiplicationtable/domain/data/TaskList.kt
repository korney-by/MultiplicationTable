package com.korneysoft.multiplicationtable.domain.data

import com.korneysoft.multiplicationtable.domain.entities.Task

interface TaskList {
    val startNumber: Int
    val finishNumber: Int
    val tasks: List<Task>

    fun getStudyAllUniqueTasks(): List<Task>
    fun getStudyTasksOnNumber(number: Int): List<Task>
}
