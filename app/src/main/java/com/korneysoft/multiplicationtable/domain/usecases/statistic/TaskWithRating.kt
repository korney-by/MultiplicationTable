package com.korneysoft.multiplicationtable.domain.usecases.statistic

import com.korneysoft.multiplicationtable.domain.entities.Task
import com.korneysoft.multiplicationtable.domain.entities.TaskRating

data class TaskWithRating(
    val parameter1: Int,
    val parameter2: Int,
    val result: Int,
    val rating: TaskRating
) {
    override fun toString() = result.toString()
}