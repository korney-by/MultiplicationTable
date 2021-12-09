package com.korneysoft.multiplicationtable.domain.usecases.statistic

import com.korneysoft.multiplicationtable.domain.entities.TaskRating
import javax.inject.Inject

class ClearStatisticUseCase @Inject constructor(
    private val loadStatisticUseCase: LoadStatisticUseCase,
    private val saveStatisticUseCase: SaveStatisticUseCase,
    private val getStatisticListUseCase:GetStatisticListUseCase
) {
    operator fun invoke(){
        val statisticList = getStatisticListUseCase()
        statisticList.forEach{
            it.rating= TaskRating.NOT_STUDIED
        }
        loadStatisticUseCase(statisticList)
        saveStatisticUseCase(statisticList)
    }
}
