package com.korneysoft.multiplicationtable

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.korneysoft.multiplicationtable.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.buttonStudy.setOnClickListener {
            openSelect(true)
        }

        binding.buttonCheck.setOnClickListener {
            openSelect(false)
        }
        binding.buttonProgress.setOnClickListener { }
        binding.buttonSettings.setOnClickListener { }


    }

    private fun openSelect(isStudyMode: Boolean) {
        val titleSelectFragment = getString(
            if (isStudyMode) R.string.learning_multiplication else R.string.knowledge_сheck
        )

        findNavController().navigate(
            R.id.action_homeFragment_to_selectFragment,
            bundleOf(
                SelectFragment.ARG_MODE_IS_STUDY to isStudyMode,
                SelectFragment.ARG_MODE_TITLE to titleSelectFragment
            )
        )
    }
}
