package com.korneysoft.multiplicationtable.ui.fragments.statistic

import androidx.lifecycle.ViewModel
import com.korneysoft.multiplicationtable.domain.usecases.statistic.GetStatisticListUseCase
import com.korneysoft.multiplicationtable.domain.usecases.task.GetFinishNumberUseCase
import com.korneysoft.multiplicationtable.domain.usecases.task.GetStartNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val getStartNumberUseCase: GetStartNumberUseCase,
    private val getFinishNumberUseCase: GetFinishNumberUseCase,
    private val getStatisticListUseCase: GetStatisticListUseCase
) : ViewModel() {

    val columnCount: Int = getFinishNumberUseCase() - getStartNumberUseCase()
    val stringCount: Int = columnCount

    val statisticList = getStatisticListUseCase()


}