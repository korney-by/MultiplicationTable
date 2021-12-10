package com.korneysoft.multiplicationtable.domain.usecases.statistic

import com.korneysoft.multiplicationtable.application.AppPreferences
import javax.inject.Inject

class SaveStatisticUseCase @Inject constructor(private val appPreferences: AppPreferences) {
    operator fun invoke(statisticList: List<TaskWithRating>? = null) {
        appPreferences.saveStatistic(statisticList)
    }
}
