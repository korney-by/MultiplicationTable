package com.korneysoft.multiplicationtable.fragments.test

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.domain.entities.*
import com.korneysoft.multiplicationtable.domain.usecases.task.GetTestListUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlayRightUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlaySoundUseCase
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
class TestViewModel @Inject constructor(
    private val playSoundUseCase: PlaySoundUseCase,
    private val playRightUseCase: PlayRightUseCase,
    private val getTestListUseCase: GetTestListUseCase
) : ViewModel() {

    private var currentTestTaskInd: Int = -1
    private var currAnswer: Int? = null

    private var testList = listOf<Task>()
    private var testJob: Job? = null

    private var _commandFlow = MutableSharedFlow<Pair<Command, Int?>>()
    val commandFlow = _commandFlow.asSharedFlow()

    private var _testTimerStateFlow = MutableStateFlow<Long?>(null)
    val timerStateFlow = _testTimerStateFlow.asStateFlow()

    private var _processStateStateFlow = MutableStateFlow<ProcessState>(ProcessState.NOT_STARTED)
    val processStateStateFlow = _processStateStateFlow.asStateFlow()

    private var _taskStateStateFlow = MutableStateFlow<ProcessState>(ProcessState.NOT_STARTED)
    val taskStateStateFlow = _taskStateStateFlow.asStateFlow()


    fun setNumberToTest(number: Int) {
        //playTestByNumUseCase.execute(number)
        testList = getTestListUseCase.execute(number).shuffled()
    }

    fun setProcessState(state: ProcessState) {
        viewModelScope.launch { _processStateStateFlow.emit(state) }
    }

    fun setTaskState(state: ProcessState) {
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
            _commandFlow.emit(Command.getCommandPair(Command.PROCESS_START))
            if (startTaskNum == 0) {
                //playAnswerUseCase.execute()
                delay(TestTime.DELAY_FOR_START_MS)
            }
            for (i in startTaskNum..testList.size - 1) {
                currentTestTaskInd = i
                _commandFlow.emit(Command.getCommandPair(Command.TASK_START, currentTestTaskInd))
                runTaskTestTimer(currentTestTaskInd)
            }
            _commandFlow.emit(Command.getCommandPair(Command.PROCESS_FINISH))
            testJob = null
        }
    }

    suspend fun runTaskTestTimer(taskIndex: Int) {
        val task = testList[taskIndex]
        var leftTime = ResponseTime.RESPONSE_TIME_MAX
        val startTimeMs = getCurrentTime()
        currAnswer = null

        playSoundUseCase.execute(task.getId())
        delay(playSoundUseCase.duration)

        while (leftTime > 0 && !isAnswerRight(task)) {
            delay(TestTime.INTERVAL_UPDATE_TIMER)
            leftTime = getLeftTime(startTimeMs, ResponseTime.RESPONSE_TIME_MAX)
            _testTimerStateFlow.emit(leftTime)
        }
        _commandFlow.emit(Command.getCommandPair(Command.TASK_STOP))

        if (isAnswerRight(task)) {
            playRightUseCase.execute()
            delay(playRightUseCase.duration)
        } else {
            playSoundUseCase.execute(task.getIdWithResult())
            delay(playSoundUseCase.duration)
        }
        _commandFlow.emit(Command.getCommandPair(Command.TASK_FINISH))
    }


    fun getLeftTime(startTimeMs: Long, leftTimeMs: Long): Long {
        return leftTimeMs - (getCurrentTime() - startTimeMs)
    }

    fun getCurrentTime(): Long {
        return SystemClock.elapsedRealtime()
    }
}
