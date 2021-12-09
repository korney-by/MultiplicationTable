package com.korneysoft.multiplicationtable.ui.fragments.statistic

import androidx.lifecycle.ViewModel
import com.korneysoft.multiplicationtable.domain.entities.TaskRating
import com.korneysoft.multiplicationtable.domain.usecases.statistic.ClearStatisticUseCase
import com.korneysoft.multiplicationtable.domain.usecases.statistic.GetStatisticListUseCase
import com.korneysoft.multiplicationtable.domain.usecases.statistic.TaskWithRating
import com.korneysoft.multiplicationtable.domain.usecases.task.GetFinishNumberUseCase
import com.korneysoft.multiplicationtable.domain.usecases.task.GetStartNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val getStartNumberUseCase: GetStartNumberUseCase,
    private val getFinishNumberUseCase: GetFinishNumberUseCase,
    private val getStatisticListUseCase: GetStatisticListUseCase,
    private val clearStatisticUseCase: ClearStatisticUseCase,
) : ViewModel() {

    val columnCount: Int get() = getFinishNumberUseCase() - getStartNumberUseCase() + 2
    val statisticList get() = getStatisticListWithTitles()

    private fun getStatisticListWithTitles(): List<TaskWithRating> {
        val newList = mutableListOf<TaskWithRating>()
        val statList = getStatisticListUseCase()

        for (i in 0 until columnCount) {
            val title = i + 1
            val taskWithRating = TaskWithRating(
                "",
                0,
                0,
                if (title >= getStartNumberUseCase()) title else 0,
                TaskRating.NOT_STUDIED
            )
            newList.add(taskWithRating)
        }
        for (i in statList.indices) {
            if ((i % (columnCount - 1)) == 0) {
                val title = i / 8 + 2
                val taskWithRating = TaskWithRating(
                    "",
                    0,
                    0,
                    if (title >= getStartNumberUseCase()) title else 0,
                    TaskRating.NOT_STUDIED
                )
                newList.add(taskWithRating)
            }
            newList.add(statList[i])
        }
        return newList
    }

    fun clearStatistic() {
        clearStatisticUseCase()
    }

}
