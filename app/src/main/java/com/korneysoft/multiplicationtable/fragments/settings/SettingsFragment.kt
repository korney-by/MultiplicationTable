package com.korneysoft.multiplicationtable.fragments.settings

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import com.korneysoft.multiplicationtable.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "T7-SettingsFragment"

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    private val viewMmodel by viewModels<SettingsViewModel>()
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewMmodel.voiceSpeedStateFlow.collect {
                    //Log.d(TAG, "voiceSpeedStateFlow.collect")
                    voiceSpeedSeekBar?.value = it
                    setTitleVoiceSpeed(it)
                }
            }
        }
    }

    private fun setListVoiceSettings() {
        val voiceList: ListPreference? =
            findPreference(getString(R.string.key_voice)) as ListPreference?

        voiceList?.apply {
            val e = viewMmodel.voices
            entries = getNamesVoices(e).toTypedArray()
            entryValues = viewMmodel.voices.toTypedArray()
            value = viewMmodel.voice

            setOnPreferenceChangeListener { preference, newValue ->
                viewMmodel.setVoice(newValue as String)
                true
            }
        }
    }


    private fun setSeekBarSettings() {
        voiceSpeedSeekBar?.apply {
            min = viewMmodel.VOICE_SPEED_MIN
            max = viewMmodel.VOICE_SPEED_MAX
            setOnPreferenceChangeListener { preference, newValue ->
                viewMmodel.setVoiceSpeed(newValue as Int)
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