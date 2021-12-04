package com.korneysoft.multiplicationtable.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import com.korneysoft.multiplicationtable.R
import com.korneysoft.multiplicationtable.fragments_viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

private const val TAG = "T7-SettingsFragment"

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    private val model by viewModels<SettingsViewModel>()
    private var voiceSpeedSeekBar: SeekBarPreference? = null
    private val isSupportVoiceSpeed = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        voiceSpeedSeekBar =
            findPreference(getString(R.string.key_voice_speed)) as SeekBarPreference?

        voiceSpeedSeekBar?.isVisible = isSupportVoiceSpeed
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListVoiceSettings()

        if (isSupportVoiceSpeed) {
            setSeekBarSettings()
            lunchCollectVoiceSpeedStateFlow()
        }
    }

    private fun lunchCollectVoiceSpeedStateFlow() {
        if (!isSupportVoiceSpeed) return

        lifecycleScope.launchWhenStarted {
            model.voiceSpeedStateFlow.collect {
                Log.d(TAG, "voiceSpeedStateFlow.collect")
                voiceSpeedSeekBar?.value = it
                setTitleVoiceSpeed(it)
            }
        }
    }

    private fun setListVoiceSettings() {
        val voiceList: ListPreference? =
            findPreference(getString(R.string.key_voice)) as ListPreference?

        voiceList?.apply {
            entries = getNamesVoices(model.voices).toTypedArray()
            entryValues = model.voices.toTypedArray()
            value = model.defaultVoice

            setOnPreferenceChangeListener { preference, newValue ->
                model.setVoice(newValue as String)
                true
            }
        }
    }


    private fun setSeekBarSettings() {
        voiceSpeedSeekBar?.apply {
            min = model.VOICE_SPEED_MIN
            max = model.VOICE_SPEED_MAX
            setOnPreferenceChangeListener { preference, newValue ->
                model.setVoiceSpeed(newValue as Int)
                true
            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun setTitleVoiceSpeed(value: Int) {
        voiceSpeedSeekBar?.apply {
            title = getString(R.string.speaking_speed, value)
        }
    }

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
}
