package com.korneysoft.multiplicationtable.application

import android.content.Context
import androidx.preference.PreferenceManager
import com.korneysoft.multiplicationtable.R
import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Preferences @Inject constructor(@ApplicationContext val appContext: Context) {

    @Inject
    lateinit var soundRepository: SoundRepository

    fun load() {
        fun applyPreferences() {
            appContext.let {
                val prefs = PreferenceManager.getDefaultSharedPreferences(appContext)
                val currVoice = prefs.getString(
                    appContext.getString(R.string.key_voice),
                    soundRepository.defaultVoice
                )
                if (currVoice != null) {
                    soundRepository.setVoice(currVoice)
                }
            }

        }
    }
}
