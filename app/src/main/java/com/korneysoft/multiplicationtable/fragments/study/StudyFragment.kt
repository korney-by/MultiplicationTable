package com.korneysoft.multiplicationtable.fragments.study

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.korneysoft.multiplicationtable.R
import com.korneysoft.multiplicationtable.databinding.FragmentStudyBinding
import com.korneysoft.multiplicationtable.domain.entities.ProcessStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudyFragment : Fragment(R.layout.fragment_study) {

    private val viewModel by viewModels<StudyViewModel>()

    private lateinit var binding: FragmentStudyBinding
    private val args: StudyFragmentArgs by navArgs()
    private val recyclerViewAdapter = StudyTaskListAdapter()
    private val studyTaskList by lazy { viewModel.studyTaskList }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStudyBinding.bind(view)

        setVisibleButtons()

        binding.buttonExit.setOnClickListener {
            findNavController().popBackStack()
        }
        if (savedInstanceState == null) {
            viewModel.setNumberToStudy(args.studyByNumber)
        }
        binding.recyclerMemoryList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }

        binding.buttonStopRepeat.setOnClickListener {
            actionButtonStopRepeat()
        }

        binding.buttonStart.setOnClickListener {
            viewModel.startStudyProcess()
        }

        binding.textAction.text = args.subTitleFragment

        updateRecyclerView()
        launchCollectTask()
    }

    private fun actionButtonStopRepeat() {
        when (viewModel.studyProcessState) {
            ProcessStatus.RUNNING -> {
                viewModel.stopStudyProcess()
            }
            ProcessStatus.STOPPED -> {
                viewModel.continueStudyProcess()
            }
            ProcessStatus.FINISHED -> {
                viewModel.startStudyProcess()
            }
            ProcessStatus.NOT_RUNNING -> {}
        }
    }

    private fun setVisibleButtons() {
        val isNotRunned = (viewModel.studyProcessState == ProcessStatus.NOT_RUNNING)
        binding.buttonStart.isVisible = isNotRunned
        binding.buttonStopRepeat.isVisible = !isNotRunned
        binding.textInstruction.isVisible = !isNotRunned

        when (viewModel.studyProcessState) {
            ProcessStatus.RUNNING -> {
                binding.buttonStopRepeat.text = getString(R.string.stop)
            }
            ProcessStatus.STOPPED -> {
                binding.buttonStopRepeat.text = getString(R.string.proceed)
            }
            ProcessStatus.FINISHED -> {
                binding.buttonStopRepeat.text = getString(R.string.repeat)
            }
            ProcessStatus.NOT_RUNNING -> {}
        }
    }

    private fun launchCollectTask() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.studyTaskStateFlow.collect {
                    when (it) {
                        StudyViewModel.STUDY_PROCESS_START -> {
                            viewModel.setProcessStatus(it)
                            studyProcessWasStarted()
                        }
                        StudyViewModel.STUDY_PROCESS_STOP -> {
                            viewModel.setProcessStatus(it)
                            studyProcessWasStopped()
                        }
                        StudyViewModel.STUDY_PROCESS_FINISH -> {
                            viewModel.setProcessStatus(it)
                            studyProcessWasFinished()
                        }
                        else -> { // normal process
                            updateRecyclerView()
                        }
                    }
                }
            }
        }
    }

    private fun studyProcessWasStarted() {
        setVisibleButtons()
        updateRecyclerView()
    }

    private fun studyProcessWasStopped() {
        setVisibleButtons()
    }

    private fun studyProcessWasFinished() {
        setVisibleButtons()
    }

    private fun updateRecyclerView() {
        recyclerViewAdapter.submitList(studyTaskList.toList())
        if (studyTaskList.size > 0) {
            binding.recyclerMemoryList.smoothScrollToPosition(studyTaskList.size - 1)
        }
    }
}
