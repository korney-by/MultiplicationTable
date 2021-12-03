package com.korneysoft.multiplicationtable.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SeekBarPreference
import com.korneysoft.multiplicationtable.R
import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.fragments_viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    private val model by viewModels<SettingsViewModel>()
    private var voiceSpeedSeekBar: SeekBarPreference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListVoiceSettings()

        voiceSpeedSeekBar =
            findPreference(getString(R.string.key_voice_speed)) as SeekBarPreference?
        setSeekBarSettings()
        lunchCollectVoiceSpeedStateFlow()
    }

    private fun lunchCollectVoiceSpeedStateFlow() {
        lifecycleScope.launchWhenStarted {
            model.voiceSpeedStateFlow.collect {
                voiceSpeedSeekBar?.value = it
                setTitleVoiceSpeed(it)
            }
        }
    }

    private fun setListVoiceSettings() {
        val voiceList: ListPreference? =
            findPreference(getString(R.string.key_voice)) as ListPreference?

        voiceList?.let {
            it.entries = getNamesVoices(model.voices).toTypedArray()
            it.entryValues = model.voices.toTypedArray()

            if (it.value == null) {
                it.value = model.defaultVoice
            }

            it.setOnPreferenceChangeListener { preference, newValue ->
                model.setVoice(newValue as String)
                true
            }
        }
    }

    private fun setSeekBarSettings() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            voiceSpeedSeekBar?.isVisible = false
            return
        }

        voiceSpeedSeekBar?.let {
            it.min = model.VOICE_SPEED_MIN
            it.max = model.VOICE_SPEED_MAX
            // it.title = getTitleVoiceSpeed(it.value)
            it.setOnPreferenceChangeListener { preference, newValue ->
                model.setVoiceSpeed(newValue as Int)
                // preference.title = getTitleVoiceSpeed(newValue)
                true
            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun setTitleVoiceSpeed(value: Int) {
        voiceSpeedSeekBar?.let {
            it.title = getString(R.string.speaking_speed, value)
        }
    }

//    @SuppressLint("StringFormatInvalid")
//    private fun getTitleVoiceSpeed(value: Int): String {
//        return getString(R.string.speaking_speed, value)
//    }

    private fun getNamesVoices(list: List<String>): List<String> {
        val namesList = mutableListOf<String>()
        list.forEach { namesList.add(getStringResourceByName(it)) }
        return namesList
    }

    private fun getStringResourceByName(stringName: String): String {
        context?.apply {
            val packageName: String = packageName
            val resId = resources.getIdentifier(stringName, "string", packageName)
            return getString(resId)
        }
        return stringName
    }

    companion object {

        fun applyPreferences(context: Context?, soundRepository: SoundRepository) {
            context?.let {
                val prefs = PreferenceManager.getDefaultSharedPreferences(context)
                val currVoice = prefs.getString(
                    context.getString(R.string.key_voice),
                    soundRepository.defaultVoice
                )
                currVoice?.let { soundRepository.setVoice(currVoice) }

                val voiceSpeed = prefs.getInt(
                    context.getString(R.string.key_voice_speed),
                    soundRepository.VOICE_SPEED_DEFAULT
                )
                soundRepository.setVoiceSpeed(voiceSpeed)
            }

        }
    }
}