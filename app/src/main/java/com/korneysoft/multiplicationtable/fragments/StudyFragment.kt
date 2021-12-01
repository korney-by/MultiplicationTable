package com.korneysoft.multiplicationtable.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.korneysoft.multiplicationtable.R
import com.korneysoft.multiplicationtable.fragments_viewmodel.StudyViewModel
import com.korneysoft.multiplicationtable.databinding.FragmentStudyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyFragment : Fragment(R.layout.fragment_study) {

    private lateinit var binding: FragmentStudyBinding
    private val args: StudyFragmentArgs by navArgs()

    val model by viewModels<StudyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStudyBinding.bind(view)

        binding.buttonExit.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonStart.setOnClickListener {
            model.study(1)
        }

        binding.textAction.text = args.subTitleFragment
    }

}
