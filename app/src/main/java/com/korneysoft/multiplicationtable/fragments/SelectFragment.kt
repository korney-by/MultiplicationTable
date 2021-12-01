package com.korneysoft.multiplicationtable.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.korneysoft.multiplicationtable.R
import com.korneysoft.multiplicationtable.databinding.FragmentSelectBinding

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

            buttonBy2.setOnClickListener { openSelectedVariant(NUMBER_2) }
            buttonBy3.setOnClickListener { openSelectedVariant(NUMBER_3) }
            buttonBy4.setOnClickListener { openSelectedVariant(NUMBER_4) }
            buttonBy5.setOnClickListener { openSelectedVariant(NUMBER_5) }
            buttonBy6.setOnClickListener { openSelectedVariant(NUMBER_6) }
            buttonBy7.setOnClickListener { openSelectedVariant(NUMBER_7) }
            buttonBy8.setOnClickListener { openSelectedVariant(NUMBER_8) }
            buttonBy9.setOnClickListener { openSelectedVariant(NUMBER_9) }
            buttonByWholeTable.setOnClickListener { openSelectedVariant(WHOLE_TABLE) }
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
            getSubTitleNextFragment(selectedNumber)
        )
        findNavController().navigate(direction)
    }

    private fun openTest(selectedNumber: Int) {
        val direction = SelectFragmentDirections.actionSelectFragmentToTestFragment(
            selectedNumber,
            getSubTitleNextFragment(selectedNumber)
        )
        findNavController().navigate(direction)
    }

    private fun getSubTitleNextFragment(studyByNumber: Int): String {
        return if (studyByNumber == WHOLE_TABLE) {
            getString(R.string.whole_table)
        } else {
            getString(R.string.multiplication_by, studyByNumber)
        }
    }

    private fun openSelectCheck() {
        val isStudyMode = false
        val direction = SelectFragmentDirections.actionSelectFragmentSelf(
            isStudyMode,
            getString(R.string.сheck)
        )
        findNavController().navigate(direction)
    }

    companion object {
        const val NUMBER_2 = 2
        const val NUMBER_3 = 3
        const val NUMBER_4 = 4
        const val NUMBER_5 = 5
        const val NUMBER_6 = 6
        const val NUMBER_7 = 7
        const val NUMBER_8 = 8
        const val NUMBER_9 = 9
        const val WHOLE_TABLE = 0
    }
}
