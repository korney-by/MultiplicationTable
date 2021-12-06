package com.korneysoft.multiplicationtable.fragments.study

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korneysoft.multiplicationtable.domain.entities.ProcessStatus
import com.korneysoft.multiplicationtable.domain.entities.Task
import com.korneysoft.multiplicationtable.domain.usecases.GetStudyListUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlayLearnByNumUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlayRepeatUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlaySoundUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

private const val TAG = "T7-StudyViewModel"

@HiltViewModel
class StudyViewModel @Inject constructor(
    private val getStudyListUseCase: GetStudyListUseCase,
    private val playSoundUseCase: PlaySoundUseCase,
    private val playRepeatUseCase: PlayRepeatUseCase,
    private val playStudyByNumUseCase: PlayLearnByNumUseCase
) :
    ViewModel() {

    val studyTaskList = mutableListOf<Task>()
    private var studyList = listOf<Task>()
    private var studyJob: Job? = null
    private var startStudyTaskNum: Int = 0
    var studyProcessState: ProcessStatus = ProcessStatus.NOT_RUNNED
        private set

    private var _studyTaskStateFlow = MutableStateFlow<Int?>(null)
    val studyTaskStateFlow = _studyTaskStateFlow.asStateFlow()

    fun stopStudyProcess() {
        studyJob?.cancel()
        studyJob = null
        viewModelScope.launch {
            _studyTaskStateFlow.emit(STUDY_PROCESS_STOP)
        }
    }

    fun restartStudyProcess() {
        stopStudyProcess()
        startStudyProcess()
    }

    fun setNumberToStudy(number: Int) {
        playStudyByNumUseCase.execute(number)
        studyList = getStudyListUseCase.execute(number)
    }

    fun setProcessStatus(studyProcessMessage: Int) {
        studyProcessState = when (studyProcessMessage) {
            STUDY_PROCESS_START -> ProcessStatus.RUNNED
            STUDY_PROCESS_STOP -> ProcessStatus.STOPPED
            STUDY_PROCESS_FINISH -> ProcessStatus.FINISHED
            else -> ProcessStatus.NOT_RUNNED
        }
    }

    fun startStudyProcess() {
        studyTaskList.clear()
        startStudyTaskNum = 0
        continueStudyProcess()
    }

    fun continueStudyProcess() {
        if (studyJob != null) return
        studyJob = viewModelScope.launch {
            _studyTaskStateFlow.emit(STUDY_PROCESS_START)
            playRepeatUseCase.execute()
            delay((2000).toLong())
            val startTaskNum = startStudyTaskNum
            for (i in startTaskNum..studyList.size - 1) {
                val task = studyList[i]
                studyTaskList.add(task)
                _studyTaskStateFlow.emit(i)
                startStudyTaskNum = i + 1
                playSoundUseCase.execute(task.getIdWithResult())
                //Log.d(TAG, task.toStringWithResult())
                delay((4 * 1000).toLong())
            }
            _studyTaskStateFlow.emit(STUDY_PROCESS_FINISH)
            studyJob = null
        }
    }

    companion object {
        const val STUDY_PROCESS_START = Int.MIN_VALUE
        const val STUDY_PROCESS_STOP = Int.MAX_VALUE - 1
        const val STUDY_PROCESS_FINISH = Int.MAX_VALUE
    }
}
