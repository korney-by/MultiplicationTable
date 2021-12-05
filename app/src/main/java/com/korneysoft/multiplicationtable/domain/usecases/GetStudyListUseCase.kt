package com.korneysoft.multiplicationtable.domain.usecases

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.domain.entities.Task
import com.korneysoft.multiplicationtable.domain.entities.TaskList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStudyListUseCase {
//    fun execute(number: Int): Flow<List<Task>> {
//        val studyList= TaskList.getTasksOnNumber(number)
//        return flow {
//            emit(studyList)
//        }

    fun execute(number: Int): List<Task> {
        return TaskList.getTasksOnNumber(number)
    }
}
