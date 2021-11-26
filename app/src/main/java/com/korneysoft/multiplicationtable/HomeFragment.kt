package com.korneysoft.multiplicationtable

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        binding.buttonProgress.setOnClickListener {

            playAssetSound("Irina/2x2=")

        }
        binding.buttonSettings.setOnClickListener { }
    }

    private fun playAssetSound(assetName: String) {

        val afd: AssetFileDescriptor? =
            context?.applicationContext?.assets?.openFd("sounds/$assetName.mp3")
        afd?.let {
            try {
                val mMediaPlayer = MediaPlayer()
                mMediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()
                mMediaPlayer.prepare()
                mMediaPlayer.start()
            } catch (ex: Exception) {
                Toast.makeText(context?.applicationContext, ex.message, Toast.LENGTH_LONG).show()
            }
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
