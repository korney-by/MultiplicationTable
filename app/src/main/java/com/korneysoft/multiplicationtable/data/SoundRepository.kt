package com.korneysoft.multiplicationtable.data

import android.content.Context
import android.content.res.AssetFileDescriptor
import java.io.IOException

class SoundRepository(val context: Context) {

    val voices: List<String>
    private var currentVoiceFolder: String = NONE

    init {
        voices = readVoices()
        setCurrentVoice(0)
    }

    private fun readVoices(): List<String> {
        val fileList = context.assets.list(SOUNDS_FOLDER)
        return fileList?.toList() ?: listOf()
    }

    fun getFileDescriptor(taskId: String): AssetFileDescriptor? {
        val fileName = getSoundFileName(taskId)
        if (fileName == NONE) return null

        return try {
            context.assets.openFd("$SOUNDS_FOLDER/$fileName")
        } catch (e: IOException) {
            null
        }
    }

    fun setCurrentVoice(index: Int) {
        if (index < voices.size) {
            currentVoiceFolder = voices[index]
        } else {
            currentVoiceFolder = NONE
        }
    }

    fun setCurrentVoice(folderName: String) {
        if (voices.contains(folderName)) {
            currentVoiceFolder = folderName
        } else {
            currentVoiceFolder = NONE
        }
    }

    private fun getSoundFileName(taskId: String): String {
        if (currentVoiceFolder == NONE) {
            return NONE
        }
        return "$currentVoiceFolder/${taskId+SOUND_FILE_TYPE}"
    }

    companion object {
        private const val SOUNDS_FOLDER = "sounds"
        private const val SOUND_FILE_TYPE = ".mp3"
        const val NONE = ""
    }
}
