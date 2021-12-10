package com.korneysoft.multiplicationtable.ui.fragments.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korneysoft.multiplicationtable.domain.entities.Task
import com.korneysoft.multiplicationtable.domain.usecases.task.GetStudyListUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlayRepeatUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlaySoundUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlayStudyByNumUseCase
import com.korneysoft.multiplicationtable.ui.utils.ProcessState
import com.korneysoft.multiplicationtable.ui.utils.StudyTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyViewModel @Inject constructor(
    private val getStudyListUseCase: GetStudyListUseCase,
    private val playSoundUseCase: PlaySoundUseCase,
    private val playRepeatUseCase: PlayRepeatUseCase,
    private val playStudyByNumUseCase: PlayStudyByNumUseCase
) :
    ViewModel() {

    val studyTaskList = mutableListOf<Task>()
    private var studyList = listOf<Task>()
    private var studyJob: Job? = null
    var studyProcessState: ProcessState = ProcessState.NOT_STARTED
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

    fun setNumberToStudy(number: Int) {
        playStudyByNumUseCase(number)
        studyList = getStudyListUseCase(number)
    }

    fun setProcessStatus(studyProcessMessage: Int) {
        studyProcessState = when (studyProcessMessage) {
            STUDY_PROCESS_START -> ProcessState.STARTED
            STUDY_PROCESS_STOP -> ProcessState.STOPPED
            STUDY_PROCESS_FINISH -> ProcessState.FINISHED
            else -> ProcessState.NOT_STARTED
        }
    }

    fun startStudyProcess() {
        studyTaskList.clear()
        continueStudyProcess()
    }

    fun continueStudyProcess() {
        if (studyJob != null) return
        studyJob = viewModelScope.launch {
            _studyTaskStateFlow.emit(STUDY_PROCESS_START)
            playRepeatUseCase()
            delay((StudyTime.DELAY_FOR_START_MS).toLong())
            val startTaskNum = studyTaskList.size
            for (i in startTaskNum until studyList.size) {
                val task = studyList[i]
                studyTaskList.add(task)
                _studyTaskStateFlow.emit(i)
                playSoundUseCase(task.getIdWithResult())
                delay((StudyTime.DELAY_FOR_REPEAT_MS).toLong())
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
