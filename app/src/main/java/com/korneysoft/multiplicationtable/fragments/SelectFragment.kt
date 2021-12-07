package com.korneysoft.multiplicationtable.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.korneysoft.multiplicationtable.R
import com.korneysoft.multiplicationtable.databinding.FragmentSelectBinding
import com.korneysoft.multiplicationtable.domain.entities.StudyNumber

class SelectFragment : Fragment(R.layout.fragment_select) {

    private val args: SelectFragmentArgs by navArgs()
    private var isStudyMode: Boolean = true
    private lateinit var binding: FragmentSelectBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSelectBinding.bind(view)
        isStudyMode = args.isStudy

        binding.buttonCheck.isVisible = isStudyMode
        if (binding.buttonCheck.isVisible) {
            binding.buttonCheck.setOnClickListener { openSelectCheck() }
        }
        setOtherClickListeners()
    }

    private fun setOtherClickListeners() {

        binding.apply {
            buttonBy2.setOnClickListener { openSelectedVariant(StudyNumber.NUMBER_2) }
            buttonBy3.setOnClickListener { openSelectedVariant(StudyNumber.NUMBER_3) }
            buttonBy4.setOnClickListener { openSelectedVariant(StudyNumber.NUMBER_4) }
            buttonBy5.setOnClickListener { openSelectedVariant(StudyNumber.NUMBER_5) }
            buttonBy6.setOnClickListener { openSelectedVariant(StudyNumber.NUMBER_6) }
            buttonBy7.setOnClickListener { openSelectedVariant(StudyNumber.NUMBER_7) }
            buttonBy8.setOnClickListener { openSelectedVariant(StudyNumber.NUMBER_8) }
            buttonBy9.setOnClickListener { openSelectedVariant(StudyNumber.NUMBER_9) }
            buttonByWholeTable.setOnClickListener { openSelectedVariant(StudyNumber.ALL_TABLE) }
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
        val direction = SelectFragmentDirections.actionSelectFragmentToStudyFragment(
            selectedNumber,
            getSubTitleStudyNextFragment(selectedNumber)
        )
        findNavController().navigate(direction)
    }

    private fun openTest(selectedNumber: Int) {
        val direction = SelectFragmentDirections.actionSelectFragmentToTestFragment(
            selectedNumber,
            getSubTitleStudyNextFragment(selectedNumber)
        )
        findNavController().navigate(direction)
    }

    private fun getSubTitleStudyNextFragment(studyByNumber: Int): String {
        return if (studyByNumber == StudyNumber.ALL_TABLE) {
            getString(R.string.whole_table)
        } else {
            getString(R.string.multiplication_by, studyByNumber)
        }
    }

    private fun openSelectCheck() {
        val isStudyMode = false
        val direction = SelectFragmentDirections.actionSelectFragmentSelf(
            isStudyMode,
            getString(R.string.check)
        )
        findNavController().navigate(direction)
    }
}
