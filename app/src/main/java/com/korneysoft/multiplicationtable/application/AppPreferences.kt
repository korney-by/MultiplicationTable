package com.korneysoft.multiplicationtable.application

import android.content.Context
import androidx.preference.PreferenceManager
import com.korneysoft.multiplicationtable.R
import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext val context: Context,
    val soundRepository: SoundRepository
) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun load() {
        loadSoundRepositorySettings()
    }

    fun loadSoundRepositorySettings() {
        context.let {
            val currVoice = preferences.getString(
                context.getString(R.string.key_voice),
                soundRepository.defaultVoice
            )
            currVoice?.let { soundRepository.setVoice(currVoice) }

            val voiceSpeed = preferences.getInt(
                context.getString(R.string.key_voice_speed),
                soundRepository.VOICE_SPEED_DEFAULT
            )
            soundRepository.setVoiceSpeed(voiceSpeed)
        }
    }
}
