package com.korneysoft.multiplicationtable.fragments.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korneysoft.multiplicationtable.domain.entities.ProcessStatus
import com.korneysoft.multiplicationtable.domain.entities.StudyTime
import com.korneysoft.multiplicationtable.domain.entities.Task
import com.korneysoft.multiplicationtable.domain.usecases.task.GetStudyListUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlayStudyByNumUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlayRepeatUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlaySoundUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "T7-StudyViewModel"

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
    var studyProcessState: ProcessStatus = ProcessStatus.NOT_RUNNING
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
            STUDY_PROCESS_START -> ProcessStatus.RUNNING
            STUDY_PROCESS_STOP -> ProcessStatus.STOPPED
            STUDY_PROCESS_FINISH -> ProcessStatus.FINISHED
            else -> ProcessStatus.NOT_RUNNING
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
            playRepeatUseCase.execute()
            delay((StudyTime.DELAY_FOR_START_MS).toLong())
            val startTaskNum = studyTaskList.size
            for (i in startTaskNum until studyList.size) {
                val task = studyList[i]
                studyTaskList.add(task)
                _studyTaskStateFlow.emit(i)
                playSoundUseCase.execute(task.getIdWithResult())
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
