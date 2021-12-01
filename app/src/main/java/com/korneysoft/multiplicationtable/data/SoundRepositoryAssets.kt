package com.korneysoft.multiplicationtable.data

import android.content.Context
import android.content.res.AssetFileDescriptor
import java.io.IOException
import javax.inject.Inject

class SoundRepositoryAssets @Inject constructor(val context: Context) : SoundRepository {

    val voices: List<String>
    private var currentVoiceFolder: String = NONE

    init {
        voices = readVoices()
        setCurrentVoice(voices[0])
    }

    private fun readVoices(): List<String> {
        val fileList = context.assets.list(SOUNDS_FOLDER)
        return fileList?.toList() ?: listOf()
    }

    override fun getFileDescriptor(taskId: String): AssetFileDescriptor? {
        val fileName = getSoundFileName(taskId)
        if (fileName == NONE) return null

        return try {
            context.assets.openFd("$SOUNDS_FOLDER/$fileName")
        } catch (e: IOException) {
            null
        }
    }

    override fun setCurrentVoice(voiceName: String) {
        currentVoiceFolder = if (voices.contains(voiceName)) {
            voiceName
        } else {
            NONE
        }
    }

    private fun getSoundFileName(taskId: String): String {
        if (currentVoiceFolder == NONE) {
            return NONE
        }
        return "$currentVoiceFolder/${taskId + SOUND_FILE_TYPE}"
    }

    companion object {
        private const val SOUNDS_FOLDER = "sounds"
        private const val SOUND_FILE_TYPE = ".mp3"
        const val NONE = ""
    }
}
