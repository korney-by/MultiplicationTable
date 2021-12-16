package com.korneysoft.multiplicationtable.ui.fragments.test

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korneysoft.multiplicationtable.domain.entities.ResponseTime
import com.korneysoft.multiplicationtable.domain.entities.Task
import com.korneysoft.multiplicationtable.domain.usecases.rating.SetBetterRatingUseCase
import com.korneysoft.multiplicationtable.domain.usecases.statistic.SaveStatisticUseCase
import com.korneysoft.multiplicationtable.domain.usecases.task.GetTestListUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlayErrorUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlayRightUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlaySoundUseCase
import com.korneysoft.multiplicationtable.ui.utils.Command
import com.korneysoft.multiplicationtable.ui.utils.ProcessState
import com.korneysoft.multiplicationtable.ui.utils.TestTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val playSoundUseCase: PlaySoundUseCase,
    private val playRightUseCase: PlayRightUseCase,
    private val getTestListUseCase: GetTestListUseCase,
    private val setBetterRatingUseCase: SetBetterRatingUseCase,
    private val saveStatisticUseCase: SaveStatisticUseCase,
    private val playErrorUseCase: PlayErrorUseCase
) : ViewModel() {

    private var currentTestTaskInd: Int = -1
    private var currAnswer: Int? = null

    private var testList = listOf<Task>()
    private var testJob: Job? = null

//    private var _commandFlow = MutableSharedFlow<Pair<Command, Int?>>()
//    val commandFlow = _commandFlow.asSharedFlow()

    private var _testTimerStateFlow = MutableStateFlow<Long?>(null)
    val timerStateFlow = _testTimerStateFlow.asStateFlow()

    private var _processStateStateFlow = MutableStateFlow<ProcessState>(ProcessState.NOT_STARTED)
    val processStateStateFlow = _processStateStateFlow.asStateFlow()

    private var _taskStateStateFlow = MutableStateFlow<ProcessState>(ProcessState.NOT_STARTED)
    val taskStateStateFlow = _taskStateStateFlow.asStateFlow()

    fun setNumberToTest(number: Int) {
        testList = getTestListUseCase(number).shuffled()
    }

    private fun setProcessState(state: ProcessState) {
        viewModelScope.launch { _processStateStateFlow.emit(state) }
    }

    private fun setTaskState(state: ProcessState) {
        viewModelScope.launch { _taskStateStateFlow.emit(state) }
    }

    fun getCurrentTask(): Task? {
        return getTestTask(currentTestTaskInd)
    }

    fun getTestTask(index: Int): Task? {
        return if (index >= 0 && index < testList.size) {
            testList[index]
        } else {
            null
        }
    }

    fun startTestProcess() {
        currentTestTaskInd = -1
        continueTestProcess()
    }

    fun setAnswer(answer: Int?) {
        answer.let { currAnswer = answer }
    }

    fun isAnswerRight(): Boolean {
        getCurrentTask()?.let {
            return isAnswerRight(it)
        }
        return false
    }

    private fun isAnswerRight(task: Task): Boolean {
        return (currAnswer == task.result)
    }

    fun continueTestProcess() {
        if (testJob != null) return
        testJob = viewModelScope.launch {
            val startTaskNum = currentTestTaskInd + 1
            applyCommand(Command.PROCESS_START)
            if (startTaskNum == 0) {
                delay(TestTime.DELAY_FOR_START_MS)
            }
            for (i in startTaskNum..testList.size - 1) {
                currentTestTaskInd = i
                applyCommand(Command.TASK_START)
                runTaskTimer(currentTestTaskInd)
            }
            applyCommand(Command.PROCESS_FINISH)
            testJob = null
        }
    }

    suspend fun runTaskTimer(taskIndex: Int) {
        val task = testList[taskIndex]
        var leftTime = ResponseTime.RESPONSE_TIME_MAX
        onChangeTimer(leftTime)

        currAnswer = null
        playSoundUseCase(task.getId())
        val startTimeMs = getCurrentTime()

        while (leftTime > 0 && !isAnswerRight(task)) {
            delay(TestTime.INTERVAL_UPDATE_TIMER)
            leftTime = getLeftTime(startTimeMs, ResponseTime.RESPONSE_TIME_MAX)
            onChangeTimer(leftTime)
        }
        applyCommand(Command.TASK_STOP)

        if (isAnswerRight(task)) {
            setBetterRatingUseCase(task, getCurrentTime() - startTimeMs)
            playRightUseCase()
            delay(playRightUseCase.duration)
        } else {
            playSoundUseCase(task.getIdWithResult())
            delay(playSoundUseCase.duration)
        }
        applyCommand(Command.TASK_FINISH)
    }

    fun playSoundError() {
        playErrorUseCase()
    }

    private fun getLeftTime(startTimeMs: Long, leftTimeMs: Long): Long {
        return leftTimeMs - (getCurrentTime() - startTimeMs)
    }

    private fun getCurrentTime(): Long {
        return SystemClock.elapsedRealtime()
    }

    fun saveStatistic() {
        viewModelScope.launch {
            saveStatisticUseCase()
        }
    }

    private suspend fun onChangeTimer(time:Long){
        _testTimerStateFlow.emit(time)
    }

    private fun applyCommand(command: Command) {
        when (command) {
            Command.PROCESS_START -> {
                setProcessState(ProcessState.STARTED)
            }
            Command.PROCESS_STOP -> {
                setProcessState(ProcessState.STOPPED)
            }
            Command.PROCESS_FINISH -> {
                setProcessState(ProcessState.FINISHED)
            }
            Command.TASK_START -> {
                setTaskState(ProcessState.STARTED)
            }
            Command.TASK_STOP -> {
                setTaskState(ProcessState.STOPPED)
            }
            Command.TASK_FINISH -> {
                setTaskState(ProcessState.FINISHED)
            }
        }
    }
}
