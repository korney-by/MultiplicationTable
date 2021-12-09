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

    private val ratingMap = mutableMapOf<String, TaskRating>()
    private val defaultRating=TaskRating.NOT_STUDIED

    init {
        taskList.tasks.forEach {
            setRating(it,defaultRating)
        }
    }

    override fun getRating(task: Task): TaskRating {
        return ratingMap[task.getId()] ?: defaultRating
    }

    override fun setRating(task: Task, rating: TaskRating) {
        setRatingOfId(task.getId(),rating)
    }

    override fun setRating(taskId: String, rating: TaskRating){
        setRatingOfId(taskId,rating)
    }

    private fun setRatingOfId(taskId: String, rating: TaskRating){
        ratingMap[taskId] = rating
    }


    override fun setBetterRating(task: Task, rating: TaskRating) {
        val currentRating = getRating(task)
        setRating(task, TaskRating.getBetterRating(rating, currentRating))
    }
}
