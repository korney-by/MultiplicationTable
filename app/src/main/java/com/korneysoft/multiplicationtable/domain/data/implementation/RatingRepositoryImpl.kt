package com.korneysoft.multiplicationtable.domain.data.implementation

import android.util.Log
import com.korneysoft.multiplicationtable.domain.data.RatingRepository
import com.korneysoft.multiplicationtable.domain.data.TaskList
import com.korneysoft.multiplicationtable.domain.entities.Task
import com.korneysoft.multiplicationtable.domain.entities.TaskRating
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RatingRepositoryImpl @Inject constructor(private val taskList: TaskList) : RatingRepository {

    private val ratingList: MutableList<TaskRating>

    init {
        ratingList = ArrayList(taskList.tasks.size)
        taskList.tasks.map {
            ratingList.add(TaskRating.NOT_STUDIED)
        }

    }

    override fun getRating(task: Task): TaskRating {
        val index = getIndex(task)
        if (index !in ratingList.indices) {
            throw Exception(EXCEPTION_OUT_OF_RANGE)
        }
        return ratingList[index]
    }

    override fun setRating(task: Task, rating: TaskRating) {
        val index = getIndex(task)
        if (index !in ratingList.indices) {
            throw Exception(EXCEPTION_OUT_OF_RANGE)
        }
        Log.d("Rating", task.toString() + rating)
        ratingList[index] = rating
    }

    override fun setBetterRating(task: Task, rating: TaskRating) {
        val currentRating = getRating(task)
        setRating(task, TaskRating.getBetterRating(rating, currentRating))
    }

    private fun getIndex(task: Task): Int {
        val x = task.parameter1 - taskList.START_NUMBER
        val y = task.parameter2 - taskList.START_NUMBER
        val countNumbers = taskList.FINISH_NUMBER - taskList.START_NUMBER + 1
        return x * (countNumbers) + y
    }

    companion object {
        const val EXCEPTION_OUT_OF_RANGE = "RatingRepository not initialised!"
    }


}
