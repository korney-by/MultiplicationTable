package com.korneysoft.multiplicationtable.domain.usecases.statistic

import com.korneysoft.multiplicationtable.domain.data.RatingRepository
import com.korneysoft.multiplicationtable.domain.data.TaskList
import com.korneysoft.multiplicationtable.domain.entities.Task
import javax.inject.Inject

class GetStatisticListUseCase @Inject constructor(
    private val taskList: TaskList,
    private val ratingRepository: RatingRepository
) {
    operator fun invoke(): List<TaskWithRating> {
        val list = mutableListOf<TaskWithRating>()
        taskList.tasks.forEach {
            val taskWithRating = TaskWithRating(
                it.getId(),
                it.parameter1,
                it.parameter2,
                it.result,
                ratingRepository.getRating(it)
            )
            list.add(taskWithRating)
        }
        return list
    }
}
