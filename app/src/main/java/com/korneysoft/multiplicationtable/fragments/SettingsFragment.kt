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
import android.content.SharedPreferences

import android.R.string.no
import androidx.viewbinding.ViewBindings


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

        fillListVoices()
        setListeners()


    }

    private fun setListeners() {


    }

    private fun fillListVoices() {
        val voiceListPreference: ListPreference? =
            findPreference(getString(R.string.key_voice)) as ListPreference?

        voiceListPreference?.let {
            val entries = getNamesVoices(soundRepository.voices).toTypedArray()
            val entryValues = soundRepository.voices.toTypedArray()
            if (it.value == null) {
                it.value = entryValues[0]
            }
            voiceListPreference.setEntries(entries)
            voiceListPreference.setEntryValues(entryValues)

            voiceListPreference. setOnPreferenceChangeListener { preference, newValue ->
                applyPreferences(context, soundRepository)
                true
            }
        }

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
