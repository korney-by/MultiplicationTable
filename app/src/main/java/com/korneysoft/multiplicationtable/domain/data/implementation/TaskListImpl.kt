package com.korneysoft.multiplicationtable.domain.data.implementation

import com.korneysoft.multiplicationtable.domain.data.TaskList
import com.korneysoft.multiplicationtable.domain.entities.Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskListImpl @Inject constructor() : TaskList {
    override val startNumber = START_NUMBER
    override val finishNumber = FINISH_NUMBER

    private val _tasks = mutableListOf<Task>()
    override val tasks: List<Task> = _tasks

    init {
        buildTasks()
    }

    private fun buildTasks() {
        for (i in startNumber..finishNumber) {
            for (j in startNumber..finishNumber) {
                val task = Task(i, j, i * j, SIGN_MULTIPLICATION)
                _tasks.add(task)
            }
        }
    }

    override fun getStudyAllUniqueTasks(): List<Task> {
        val tasksOfNumber = mutableListOf<Task>()
        _tasks.forEach {
            if (it.parameter1 <= it.parameter2) {
                tasksOfNumber.add(it)
            }
        }
        return tasksOfNumber
    }

    override fun getStudyTasksOnNumber(number: Int): List<Task> {
        val tasksOfNumber = mutableListOf<Task>()
        _tasks.forEach {
            if (it.parameter1 == number) {
                tasksOfNumber.add(it)
            }
        }
        return tasksOfNumber
    }

    companion object {
        private const val START_NUMBER = 2
        private const val FINISH_NUMBER = 9
        private const val SIGN_MULTIPLICATION = 'x'
    }
}
