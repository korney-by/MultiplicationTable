package com.korneysoft.multiplicationtable

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.korneysoft.multiplicationtable.databinding.FragmentStudyBinding

class StudyFragment : Fragment(R.layout.fragment_study) {

    private lateinit var binding: FragmentStudyBinding
    private val args: StudyFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStudyBinding.bind(view)

        binding.buttonExit.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.textAction.text = args.subTitleFragment
    }

}
