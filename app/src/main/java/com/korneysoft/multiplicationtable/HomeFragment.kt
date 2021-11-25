package com.korneysoft.multiplicationtable

import android.os.Bundle
import android.view.View
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
            if (isStudyMode) R.string.learning else R.string.сheck
        )
        val direction = HomeFragmentDirections.actionHomeFragmentToSelectFragment(
            isStudyMode,
            titleSelectFragment
        )
        findNavController().navigate(direction)

    }
}
