package com.korneysoft.multiplicationtable

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.korneysoft.multiplicationtable.databinding.FragmentStudyBinding

class StudyFragment : Fragment(R.layout.fragment_study) {

    private lateinit var binding: FragmentStudyBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStudyBinding.bind(view)

        binding.buttonExit.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    companion object {
        const val ARG_STUDY_BY_NUMBER = "study_by_number"
    }

}
