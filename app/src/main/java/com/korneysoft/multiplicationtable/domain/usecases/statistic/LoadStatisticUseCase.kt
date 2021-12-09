package com.korneysoft.multiplicationtable.domain.usecases.statistic

import com.korneysoft.multiplicationtable.application.AppPreferences
import com.korneysoft.multiplicationtable.domain.data.RatingRepository
import javax.inject.Inject

class LoadStatisticUseCase @Inject constructor(
    private val ratingRepository: RatingRepository,
    private val appPreferences: AppPreferences
) {
    operator fun invoke(inputStatisticList: List<TaskWithRating>? = null) {
        val statisticList= inputStatisticList ?: appPreferences.readStatistic()
        statisticList.forEach{
            ratingRepository.setRating(it.id,it.rating)
        }

    }
}
