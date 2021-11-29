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

    fun getFileDescriptor(
        multiplier1: Int,
        multiplier2: Int,
        isHasResult: Boolean = false
    ): AssetFileDescriptor? {
        val fileName = getSoundFileName(multiplier1, multiplier2, isHasResult)
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

    private fun getSoundFileName(
        multiplier1: Int,
        multiplier2: Int,
        isHasResult: Boolean = false
    ): String {
        if (currentVoiceFolder == NONE) {
            return NONE
        }
        val fileName = SoundFileName.make(multiplier1, multiplier2, isHasResult)
        return "$currentVoiceFolder/$fileName"
    }

    companion object {
        private const val SOUNDS_FOLDER = "sounds"
        const val NONE = ""
    }
}
