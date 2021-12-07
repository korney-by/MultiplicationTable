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
import com.korneysoft.multiplicationtable.fragments.study.StudyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestFragment : Fragment(R.layout.fragment_test) {
    private val viewModel by viewModels<TestViewModel>()

    private lateinit var binding: FragmentTestBinding
    private val args: TestFragmentArgs by navArgs()
    private var currentTask: Task? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTestBinding.bind(view)

        if (savedInstanceState == null) {
            viewModel.setNumberToTest(args.testByNumber)
        }

        binding.buttonOk.setOnClickListener {
            viewModel.setAnswer(binding.editTextAnswer.text.toString().toInt())
        }
        binding.customViewTimer.setPeriod(ResponseTime.RESPONSE_TIME_MAX)
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
                            setCurrentTask(it)
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

    private fun setCurrentTask(number: Int?) {
        number?.let {
            currentTask = viewModel.getTestTask(it)
        }
    }

    private fun runTestTask(number: Int?) {
        number?.let {
            currentTask = viewModel.getTestTask(it)
            binding.textTask.text = currentTask.toString()
            binding.customViewTimer.setCurrent(0)
            binding.editTextAnswer.text.clear()
            binding.editTextAnswer.isEnabled = true
            startTimer()
        }
    }

    private fun startTimer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.testTimerStateFlow.collect {
                when (it) {
                    TestViewModel.TEST_TASK_STOP -> {
                        binding.editTextAnswer.isEnabled = false
                        binding.customViewTimer.setFinished(true)
                    }
                    TestViewModel.TEST_TASK_FINISH -> {
                        this.cancel()
                    }
                    else -> {
                        it?.let { time ->
                            binding.customViewTimer.setCurrent(time)
                        }
                    }
                }
            }
        }
    }

}
