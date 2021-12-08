package com.korneysoft.multiplicationtable.domain.entities

object TaskList {
    const val START_NUMBER = 2
    const val FINISH_NUMBER = 9
    private const val SIGN_MULTIPLICATION = 'x'

    private val _tasks = mutableListOf<Task>()
    val tasks: List<Task> = _tasks

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

    fun getStudyAllUniqueTasks(): List<Task> {
        val tasksOfNumber = mutableListOf<Task>()
        _tasks.forEach {
            if (it.parameter1 <= it.parameter2) {
                tasksOfNumber.add(it)
            }
        }
        return tasksOfNumber
    }

    fun getStudyTasksOnNumber(number: Int): List<Task> {
        val tasksOfNumber = mutableListOf<Task>()
        _tasks.forEach {
            if (it.parameter1 == number) {
                tasksOfNumber.add(it)
            }
        }
        return tasksOfNumber
    }

}
