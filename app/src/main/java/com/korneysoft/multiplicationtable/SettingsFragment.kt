package com.korneysoft.multiplicationtable

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.korneysoft.multiplicationtable.data.SoundRepository

class SettingsFragment : PreferenceFragmentCompat() {

    val repo by lazy { getRepository() }
    private fun getRepository(): SoundRepository? {
        return (activity as MainActivity).repo
    }

    val voices by lazy { getVoicesList() }
    private fun getVoicesList(): List<String> {
        repo?.let {
            return it.voices
        }
        return listOf()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillListVoices()
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
    override fun onDestroy() {
        applyPreferences()
        super.onDestroy()
    }

    private fun fillListVoices() {
        val voiceListPreference: ListPreference? =
            findPreference(getString(R.string.key_voice)) as ListPreference?

        voiceListPreference?.let {
            val entries = getNamesVoices(voices).toTypedArray()
            val entryValues = voices.toTypedArray()
            if (it.value == null) {
                it.value = entryValues[0]
            }
            voiceListPreference.setEntries(entries)
            voiceListPreference.setEntryValues(entryValues)
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
}