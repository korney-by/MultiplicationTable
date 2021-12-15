package com.korneysoft.multiplicationtable.ui.fragments.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korneysoft.multiplicationtable.domain.entities.Task
import com.korneysoft.multiplicationtable.domain.usecases.task.GetStudyListUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlayRepeatUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlaySoundUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlayStudyByNumUseCase
import com.korneysoft.multiplicationtable.ui.utils.Command
import com.korneysoft.multiplicationtable.ui.utils.ProcessState
import com.korneysoft.multiplicationtable.ui.utils.StudyTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private var _commandFlow = MutableSharedFlow<Pair<Command, Int?>>()
    val commandFlow = _commandFlow.asSharedFlow()

    private var _studyTaskStateFlow = MutableStateFlow<Int?>(null)
    val studyTaskStateFlow = _studyTaskStateFlow.asStateFlow()

    private var _processStateStateFlow = MutableStateFlow<ProcessState>(ProcessState.NOT_STARTED)
    val processStateStateFlow = _processStateStateFlow.asStateFlow()

    fun stopStudyProcess() {
        studyJob?.cancel()
        studyJob = null
        viewModelScope.launch {
            sendCommandToFragment(Command.PROCESS_STOP)
        }
    }

    fun setNumberToStudy(number: Int) {
        playStudyByNumUseCase(number)
        studyList = getStudyListUseCase(number)
    }

    fun startStudyProcess() {
        studyTaskList.clear()
        continueStudyProcess()
    }

    fun setProcessState(state: ProcessState) {
        viewModelScope.launch { _processStateStateFlow.emit(state) }
    }

    fun continueStudyProcess() {
        if (studyJob != null) return
        studyJob = viewModelScope.launch {
            sendCommandToFragment(Command.PROCESS_START)
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
            sendCommandToFragment(Command.PROCESS_FINISH)
            studyJob = null
        }
    }

    private suspend fun sendCommandToFragment(command: Command) {
        _commandFlow.emit(Command.getCommandPair(command))
    }

}
