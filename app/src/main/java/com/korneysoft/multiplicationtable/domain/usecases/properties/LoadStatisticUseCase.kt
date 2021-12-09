package com.korneysoft.multiplicationtable.domain.usecases.properties

import com.korneysoft.multiplicationtable.application.AppPreferences
import com.korneysoft.multiplicationtable.domain.data.RatingRepository
import javax.inject.Inject

class LoadStatisticUseCase @Inject constructor(
    private val ratingRepository: RatingRepository,
    private val appPreferences: AppPreferences
) {
    operator fun invoke() {
        val statisticList=appPreferences.readStatistic()
        statisticList.forEach{
            ratingRepository.setRating(it.id,it.rating)
        }

    }
}
