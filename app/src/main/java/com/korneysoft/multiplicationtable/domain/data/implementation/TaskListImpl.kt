package com.korneysoft.multiplicationtable.domain.entities

import com.korneysoft.multiplicationtable.domain.data.TaskList
import javax.inject.Singleton

@Singleton
class TaskListImpl : TaskList {
    override val START_NUMBER = _START_NUMBER
    override val FINISH_NUMBER = _FINISH_NUMBER

    private val _tasks = mutableListOf<Task>()
    override val tasks: List<Task> = _tasks

    init {
        buildTasks()
    }

    private fun buildTasks() {
        for (i in START_NUMBER..FINISH_NUMBER) {
            for (j in START_NUMBER..FINISH_NUMBER) {
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
        private const val _START_NUMBER = 2
        private const val _FINISH_NUMBER = 9
        private const val SIGN_MULTIPLICATION = 'x'
    }
}
