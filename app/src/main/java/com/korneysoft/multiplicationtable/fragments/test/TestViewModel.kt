package com.korneysoft.multiplicationtable.fragments.test

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korneysoft.multiplicationtable.domain.entities.ProcessStatus
import com.korneysoft.multiplicationtable.domain.entities.ResponseTime
import com.korneysoft.multiplicationtable.domain.entities.Task
import com.korneysoft.multiplicationtable.domain.entities.TestTime
import com.korneysoft.multiplicationtable.domain.usecases.task.GetTestListUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlaySoundUseCase
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
    private val getTestListUseCase: GetTestListUseCase
) : ViewModel() {

    private var currentTestTaskInd: Int = -1
    private var currAnswer: Int? = null

    private var testList = listOf<Task>()
    private var testJob: Job? = null


    var testProcessState: ProcessStatus = ProcessStatus.NOT_RUNNING
        private set

    private var _testTaskStateFlow = MutableStateFlow<Int?>(null)
    val testTaskStateFlow = _testTaskStateFlow.asStateFlow()

    private var _testTimerStateFlow = MutableStateFlow<Long?>(null)
    val testTimerStateFlow = _testTimerStateFlow.asStateFlow()


    fun setNumberToTest(number: Int) {
        //playTestByNumUseCase.execute(number)
        testList = getTestListUseCase.execute(number).shuffled()
    }

    fun setProcessStatus(testProcessMessage: Int) {
        testProcessState = when (testProcessMessage) {
            TEST_PROCESS_START -> ProcessStatus.RUNNING
            TEST_PROCESS_STOP -> ProcessStatus.STOPPED
            TEST_PROCESS_FINISH -> ProcessStatus.FINISHED
            else -> ProcessStatus.NOT_RUNNING
        }
    }

    private fun getCurrentTestTask(): Task? {
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

    fun setAnswer(answer: Int) {
        currAnswer = answer
    }

    private fun isAnswerRight(task: Task): Boolean {
        return (currAnswer == task.result)
    }

    fun continueTestProcess() {
        if (testJob != null) return
        testJob = viewModelScope.launch {
            val startTaskNum = currentTestTaskInd + 1
            _testTaskStateFlow.emit(TestViewModel.TEST_PROCESS_START)
            if (startTaskNum == 0) {
//                //playAnsverUseCase.execute()
                delay((TestTime.DELAY_FOR_START_MS).toLong())
            }
            for (i in startTaskNum..testList.size - 1) {
                currentTestTaskInd = i
                _testTaskStateFlow.emit(currentTestTaskInd)
                startTaskTest(currentTestTaskInd)
            }
            _testTaskStateFlow.emit(TestViewModel.TEST_PROCESS_FINISH)
            testJob = null
        }
    }

    private suspend fun startTaskTest(taskIndex: Int) {
        val task = testList[taskIndex]
        var leftTime = ResponseTime.RESPONSE_TIME_MAX
        val startTimeMs = getCurrentTime()
        currAnswer = null

        while (leftTime > 0 && !isAnswerRight(task)) {
            delay(TestTime.INTERVAL_UPDATE_TIMER)

            leftTime = getLeftTime(startTimeMs,ResponseTime.RESPONSE_TIME_MAX)
            _testTimerStateFlow.emit(leftTime)

        }
        _testTimerStateFlow.emit(TEST_TASK_STOP)
        playSoundUseCase.execute(task.getIdWithResult())
        delay(playSoundUseCase.duration)
        _testTimerStateFlow.emit(TEST_TASK_FINISH)
    }

    fun getLeftTime(startTimeMs: Long, leftTimeMs: Long): Long {
        return leftTimeMs - (getCurrentTime() - startTimeMs)
    }

    fun getCurrentTime(): Long {
        return SystemClock.elapsedRealtime()
    }

    companion object {
        const val TEST_PROCESS_START = Int.MIN_VALUE
        const val TEST_PROCESS_STOP = Int.MAX_VALUE - 1
        const val TEST_PROCESS_FINISH = Int.MAX_VALUE

        const val TEST_TASK_FINISH = Long.MAX_VALUE
        const val TEST_TASK_STOP = Long.MAX_VALUE - 1
    }
}
