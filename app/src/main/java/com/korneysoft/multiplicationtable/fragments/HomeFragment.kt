package com.korneysoft.multiplicationtable.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.korneysoft.multiplicationtable.R
import com.korneysoft.multiplicationtable.application.AppPreferences
import com.korneysoft.multiplicationtable.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.prefs.Preferences
import javax.inject.Inject

private const val TAG = "T7-HomeFragment"

@AndroidEntryPoint
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
        binding.buttonProgress.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStatisticFragment())
        }
        binding.buttonSettings.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
        }
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
