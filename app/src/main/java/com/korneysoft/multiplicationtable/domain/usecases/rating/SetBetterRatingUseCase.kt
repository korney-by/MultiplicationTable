package com.korneysoft.multiplicationtable.domain.usecases.rating

import com.korneysoft.multiplicationtable.domain.data.RatingRepository
import com.korneysoft.multiplicationtable.domain.entities.Task
import com.korneysoft.multiplicationtable.domain.entities.TaskRating
import javax.inject.Inject

class SetBetterRatingUseCase @Inject constructor(private val ratingRepository: RatingRepository) {

    operator fun invoke(task: Task, timeMs: Long) {
        val rating = TaskRating.getRatingFromResponseTime(timeMs)
        ratingRepository.setBetterRating(task, rating)
    }
}
