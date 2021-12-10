package com.korneysoft.multiplicationtable.domain.data

import com.korneysoft.multiplicationtable.domain.entities.Task
import com.korneysoft.multiplicationtable.domain.entities.TaskRating

interface RatingRepository {
    fun getRating(task: Task): TaskRating

    fun setRating(task: Task, rating: TaskRating)
    fun setRating(taskId: String, rating: TaskRating)

    fun setBetterRating(task: Task, rating: TaskRating)
}
