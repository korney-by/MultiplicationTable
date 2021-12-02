package com.korneysoft.multiplicationtable.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.korneysoft.multiplicationtable.R
import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.fragments_viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

import androidx.lifecycle.lifecycleScope
import androidx.preference.SeekBarPreference
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var soundRepository: SoundRepository

    val model by viewModels<SettingsViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListVoiceSettings()
        setSeekBarSettings()
    }

    private fun setSeekBarSettings() {
        val voiceSpeedSeekBar: SeekBarPreference? =
            findPreference(getString(R.string.key_voice_speed)) as SeekBarPreference?

        voiceSpeedSeekBar?.let {
            it.min = 50
            it.max = 200
            it.seekBarIncrement=5
            it.setOnPreferenceChangeListener { preference, newValue ->
                setVoiceSpeed(newValue as Int)

                preference.setTitle(getString(R.string.speaking_speed) + " $newValue %")
                true
            }
        }
        lunchCollectChangeVoiceSpeedFlow()
    }

    private fun setListVoiceSettings() {
        val voiceList: ListPreference? =
            findPreference(getString(R.string.key_voice)) as ListPreference?

        voiceList?.let {
            val entries = getNamesVoices(soundRepository.voices).toTypedArray()
            val entryValues = soundRepository.voices.toTypedArray()
            if (it.value == null) {
                it.value = entryValues[0]
            }
            it.setEntries(entries)
            it.setEntryValues(entryValues)

            it.setOnPreferenceChangeListener { preference, newValue ->
                setVoice(newValue as String)
                true
            }
        }
        lunchCollectChangeVoiceFlow()
    }

    private fun lunchCollectChangeVoiceFlow() {
        lifecycleScope.launchWhenStarted {
            model.onChangeVoiceFlow.collect {
                model.playTestSound()
            }
        }
    }

    private fun lunchCollectChangeVoiceSpeedFlow() {
        lifecycleScope.launchWhenStarted {
            model.onChangeVoiceSpeedFlow.collect {
                model.playTestSound()
            }
        }
    }

    private fun setVoice(voice: String) {
        soundRepository.setCurrentVoice(voice)
    }

    private fun setVoiceSpeed(speed: Int) {
        soundRepository.setVoiceSpeed(speed)
    }


    private fun getNamesVoices(list: List<String>): List<String> {
        val namesList = mutableListOf<String>()
        list.forEach {
            namesList.add(getStringResourceByName(it))
        }
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
                if (currVoice != null) {
                    soundRepository.setCurrentVoice(currVoice)
                }
            }

        }
    }
}
