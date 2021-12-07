package com.korneysoft.multiplicationtable.fragments.test

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.korneysoft.multiplicationtable.R
import com.korneysoft.multiplicationtable.databinding.FragmentTestBinding
import com.korneysoft.multiplicationtable.domain.entities.ResponseTime
import com.korneysoft.multiplicationtable.domain.entities.Task
import com.korneysoft.multiplicationtable.domain.entities.TestTime
import com.korneysoft.multiplicationtable.fragments.study.StudyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestFragment : Fragment(R.layout.fragment_test) {
    private val viewModel by viewModels<TestViewModel>()

    private lateinit var binding: FragmentTestBinding
    private val args: TestFragmentArgs by navArgs()
    private val testTaskList by lazy { viewModel.testTaskList }
    private var currentTask: Task? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTestBinding.bind(view)

        if (savedInstanceState == null) {
            viewModel.setNumberToTest(args.testByNumber)
        }

        binding.textAction.text = args.subTitleFragment
        launchCollectTask()
        viewModel.startTestProcess()
    }

    private fun launchCollectTask() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.testTaskStateFlow.collect {
                    when (it) {
                        StudyViewModel.STUDY_PROCESS_START -> {
                            viewModel.setProcessStatus(it)
                            testProcessWasStarted()
                        }
                        StudyViewModel.STUDY_PROCESS_STOP -> {
                            viewModel.setProcessStatus(it)
                            testProcessWasStopped()
                        }
                        StudyViewModel.STUDY_PROCESS_FINISH -> {
                            viewModel.setProcessStatus(it)
                            testProcessWasFinished()
                        }
                        else -> { //normal process
                            runTestTask(it)
                        }
                    }
                }
            }
        }
    }

    private fun testProcessWasStarted() {
        setVisibleButtons()
    }

    private fun testProcessWasStopped() {
        setVisibleButtons()
        currentTask = null
    }

    private fun testProcessWasFinished() {
        setVisibleButtons()
        currentTask = null
    }

    private fun setVisibleButtons() {

    }

    private fun runTestTask(number: Int?) {
        number?.let {
            currentTask = testTaskList[number]
            binding.textTask.text = currentTask.toString()
            startTimer()
        }
    }

    private fun startTimer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.testTimerStateFlow.collect {
                    it?.let { time ->
                        binding.customViewTimer.setCurrent(time)
                    }
                }
            }
        }
    }

}
