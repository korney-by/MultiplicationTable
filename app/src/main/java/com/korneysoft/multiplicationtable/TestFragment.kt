package com.korneysoft.multiplicationtable

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.korneysoft.multiplicationtable.databinding.FragmentStudyBinding
import com.korneysoft.multiplicationtable.databinding.FragmentTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestFragment:Fragment(R.layout.fragment_test) {

    private lateinit var binding: FragmentTestBinding
    private val args: TestFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentTestBinding.bind(view)

        binding.textAction.text = args.subTitleFragment
        binding.customViewPomodoroUnit

    }
}
