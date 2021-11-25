package com.korneysoft.multiplicationtable

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.korneysoft.multiplicationtable.databinding.FragmentSelectBinding

class SelectFragment : Fragment(R.layout.fragment_select) {

    private var isStudyMode: Boolean = true
    private lateinit var binding: FragmentSelectBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSelectBinding.bind(view)
        isStudyMode = requireArguments().getBoolean(ARG_MODE_IS_STUDY)

        binding.buttonCheck.isVisible = isStudyMode
        if (binding.buttonCheck.isVisible) {
            binding.buttonCheck.setOnClickListener { openSelectCheck() }
        }
        setOtherClickListeners()
    }

    private fun setOtherClickListeners() {

        binding.apply {

            buttonBy2.setOnClickListener { openSelectedVariant(NUMBER_2) }
            buttonBy3.setOnClickListener { openSelectedVariant(NUMBER_3) }
            buttonBy4.setOnClickListener { openSelectedVariant(NUMBER_4) }
            buttonBy5.setOnClickListener { openSelectedVariant(NUMBER_5) }
            buttonBy6.setOnClickListener { openSelectedVariant(NUMBER_6) }
            buttonBy7.setOnClickListener { openSelectedVariant(NUMBER_7) }
            buttonBy8.setOnClickListener { openSelectedVariant(NUMBER_8) }
            buttonBy9.setOnClickListener { openSelectedVariant(NUMBER_9) }
            buttonByRandom.setOnClickListener { openSelectedVariant(MIXED) }
        }
    }

    private fun openSelectedVariant(selectedNumber: Int) {
        if (isStudyMode) {
            openStudy(selectedNumber)
        } else {
            openTest(selectedNumber)
        }
    }

    private fun openStudy(selectedNumber: Int) {
        findNavController().navigate(
            R.id.action_selectFragment_to_studyFragment,
            bundleOf(StudyFragment.ARG_STUDY_BY_NUMBER to selectedNumber)
        )
    }

    private fun openTest(selectedNumber: Int) {
        findNavController().navigate(
            R.id.action_selectFragment_to_studyFragment,
            bundleOf(TestFragment.ARG_STUDY_BY_NUMBER to selectedNumber)
        )
    }

    private fun openSelectCheck() {
        val isStudyMode = false
        findNavController().navigate(
            R.id.action_selectFragment_self,
            bundleOf(
                SelectFragment.ARG_MODE_IS_STUDY to isStudyMode,
                SelectFragment.ARG_MODE_TITLE to getString(R.string.knowledge_сheck)
            )
        )
    }

    companion object {
        const val ARG_MODE_IS_STUDY = "isStudy"
        const val ARG_MODE_TITLE = "titleFragment"
        const val NUMBER_2 = 2
        const val NUMBER_3 = 2
        const val NUMBER_4 = 2
        const val NUMBER_5 = 2
        const val NUMBER_6 = 2
        const val NUMBER_7 = 2
        const val NUMBER_8 = 2
        const val NUMBER_9 = 2
        const val MIXED = 0
    }
}
