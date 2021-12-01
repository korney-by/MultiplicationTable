package com.korneysoft.multiplicationtable

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.korneysoft.multiplicationtable.data.SoundRepositoryAssets
import com.korneysoft.multiplicationtable.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    val repo by lazy { getSoundRepository() }
    private fun getSoundRepository(): SoundRepositoryAssets? {
        return (activity as MainActivity).repo
    }

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
        applyPreferences()
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

    private fun applyPreferences() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        repo?.let { repo ->
            val currVoice = prefs.getString(getString(R.string.key_voice), repo.voices[0])
            if (currVoice != null) {
                repo.setCurrentVoice(currVoice)
            }
        }
    }
}
