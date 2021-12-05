package com.korneysoft.multiplicationtable.domain.entities

object TaskList {
    private const val START_NUMBER = 2
    private const val FINISH_NUMBER = 9
    private const val SIGN_MULTIPLICATION = 'x'

    private val tasks = mutableListOf<Task>()

    init {
        fillTasks()
    }

    private fun fillTasks() {
        for (i in START_NUMBER..FINISH_NUMBER) {
            for (j in START_NUMBER..FINISH_NUMBER) {
                val task = Task(i, j, i * j, SIGN_MULTIPLICATION)
                tasks.add(task)
            }
        }
    }

    fun getTasksOnNumber(number: Int): List<Task> {
        val tasksOfNumber = mutableListOf<Task>()
        tasks.forEach {
            if (it.parameter1 == number || it.parameter2 == number) {
                tasksOfNumber.add(it)
            }
        }
        return tasksOfNumber
    }

}
