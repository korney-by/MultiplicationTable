package com.korneysoft.multiplicationtable.fragments.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korneysoft.multiplicationtable.domain.entities.*
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

    val testTaskList = mutableListOf<Task>()
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

    fun startTestProcess() {
        testTaskList.clear()
        continueTestProcess()
    }

    fun continueTestProcess() {
        if (testJob != null) return
        testJob = viewModelScope.launch {
            val startTaskNum = testTaskList.size
            if (startTaskNum == 0) {
                _testTaskStateFlow.emit(TestViewModel.TEST_PROCESS_START)
                //playAnsverUseCase.execute()
                delay((StudyTime.DELAY_FOR_START_MS).toLong())
            }
            for (i in startTaskNum..testList.size - 1) {
                val task = testList[i]
                testTaskList.add(task)
                _testTaskStateFlow.emit(i)

                val leftTime = ResponseTime.RESPONSE_TIME_MAX
                while (leftTime > 0) {
                    _testTimerStateFlow.emit(ResponseTime.RESPONSE_TIME_MAX-leftTime)
                    delay(TestTime.INTERVAL_UPDATE_TIMER)
                    leftTime.minus(TestTime.INTERVAL_UPDATE_TIMER)
                }
                playSoundUseCase.execute(task.getIdWithResult())
            }
            _testTaskStateFlow.emit(TestViewModel.TEST_PROCESS_FINISH)
            testJob = null
        }
    }

    companion object {
        const val TEST_PROCESS_START = Int.MIN_VALUE
        const val TEST_PROCESS_STOP = Int.MAX_VALUE - 1
        const val TEST_PROCESS_FINISH = Int.MAX_VALUE

        //        const val TEST_TASK_START=Long.MIN_VALUE
//        const val TEST_TASK_STOP=Long.MAX_VALUE
        const val TEST_TASK_STOP = Long.MAX_VALUE-1
    }
}