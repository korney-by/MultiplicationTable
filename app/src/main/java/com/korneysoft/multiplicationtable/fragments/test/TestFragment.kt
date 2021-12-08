package com.korneysoft.multiplicationtable.fragments.test

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.korneysoft.multiplicationtable.R
import com.korneysoft.multiplicationtable.databinding.FragmentTestBinding
import com.korneysoft.multiplicationtable.domain.entities.Command
import com.korneysoft.multiplicationtable.domain.entities.ProcessState
import com.korneysoft.multiplicationtable.domain.entities.ResponseTime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestFragment : Fragment(R.layout.fragment_test) {
    private val viewModel by viewModels<TestViewModel>()

    private lateinit var binding: FragmentTestBinding
    private val args: TestFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTestBinding.bind(view)

        if (savedInstanceState == null) {
            viewModel.setNumberToTest(args.testByNumber)
        }

        setListenersForEnter()

        binding.customViewTimer.setPeriod(ResponseTime.RESPONSE_TIME_MAX)
        binding.textAction.text = args.subTitleFragment
        launchCollectCommands()
        launchCollectTimer()
        launchCollectTaskState()
        launchCollectProcessState()
        viewModel.startTestProcess()
    }

    private fun setListenersForEnter() {
        binding.buttonOk.setOnClickListener {
            viewModel.setAnswer(binding.editTextAnswer.text.toString().toIntOrNull())
            if (!viewModel.isAnswerRight()) {
                binding.editTextAnswer.selectAll()
            }
        }

        binding.editTextAnswer.setOnEditorActionListener() { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    binding.buttonOk.callOnClick()
                    true
                }
                else -> false
            }
        }
    }

    private fun launchCollectCommands() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.commandFlow.collect {
                    when (it.first) {
                        Command.PROCESS_START -> {
                            viewModel.setProcessState(ProcessState.STARTED)
                        }
                        Command.PROCESS_STOP -> {
                            viewModel.setProcessState(ProcessState.STOPPED)
                        }
                        Command.PROCESS_FINISH -> {
                            viewModel.setProcessState(ProcessState.FINISHED)
                        }

                        Command.TASK_START -> {
                            viewModel.setTaskState(ProcessState.STARTED)
                            it.second?.let { numberTask ->
                                binding.editTextAnswer.text.clear()
                            }
                        }
                        Command.TASK_STOP -> {
                            viewModel.setTaskState(ProcessState.STOPPED)
                        }
                        Command.TASK_FINISH -> {
                            viewModel.setTaskState(ProcessState.FINISHED)
                        }
                    }
                }
            }
        }
    }

    private fun launchCollectTimer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.timerStateFlow.collect { time ->
                time?.let { binding.customViewTimer.setCurrent(time) }
            }
        }
    }

    private fun launchCollectTaskState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.taskStateStateFlow.collect {
                showCurrentTaskState(it)
            }
        }
    }

    private fun launchCollectProcessState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.processStateStateFlow.collect {
                showCurrentProcessState(it)
            }
        }
    }


    private fun showCurrentProcessState(state: ProcessState) {

    }

    private fun showCurrentTaskState(state: ProcessState) {
        when (state) {
            ProcessState.STARTED -> {
                binding.textTask.text = viewModel.getCurrentTask().toString()
                binding.textTask.isVisible = true
                binding.editTextAnswer.isVisible = true
                binding.editTextAnswer.requestFocus()
                showKeyboard(binding.editTextAnswer)
            }
            ProcessState.STOPPED, ProcessState.FINISHED -> {
                binding.editTextAnswer.isVisible = false
                binding.textTask.text = viewModel.getCurrentTask()?.toStringWithResult()
            }
        }
    }

    private fun showKeyboard(textView: TextView) {
        context?.let {
            val imm: InputMethodManager =
                it.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(textView.getWindowToken(), 0)
            imm.showSoftInput(textView, 0);
        }
    }


}
