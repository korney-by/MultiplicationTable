package com.korneysoft.multiplicationtable.domain.data

import android.content.Context
import android.content.res.AssetFileDescriptor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundRepositoryAssets @Inject constructor(@ApplicationContext val appContext: Context) :
    SoundRepository {

    override val NONE = Companion.NONE
    override val testSoundFileId: String = SOUND_TEST
    override val defaultVoice: String
    override val voiceName: String get() = currentVoiceFolder
    override val voiceSpeed: Int get() = currentVoiceSpeed
    override val VOICE_SPEED_DEFAULT: Int = DEFAULT_VOICE_SPEED
    override val VOICE_SPEED_MIN = MIN_VOICE_SPEED
    override val VOICE_SPEED_MAX = MAX_VOICE_SPEED
    override val voices: List<String>

    private val _onChangeVoiceFlow = MutableSharedFlow<String>(0)
    override val onChangeVoiceFlow = _onChangeVoiceFlow.asSharedFlow()

    private val _onChangeVoiceSpeedFlow = MutableSharedFlow<Int>(0)
    override val onChangeVoiceSpeedFlow = _onChangeVoiceSpeedFlow.asSharedFlow()

    private var currentVoiceFolder: String = NONE
    private var currentVoiceSpeed: Int = DEFAULT_VOICE_SPEED

    init {
        voices = readVoices()
        defaultVoice = voices[0]
        setVoice(defaultVoice)
    }

    private fun readVoices(): List<String> {
        val fileList = appContext.assets.list(SOUNDS_FOLDER)
        return fileList?.toList() ?: listOf()
    }

    override fun getSoundFileDescriptor(taskId: String): AssetFileDescriptor? {
        val fileName = getSoundFileName(taskId)
        if (fileName == NONE) return null

        return try {
            appContext.assets.openFd("$SOUNDS_FOLDER/$fileName")
        } catch (e: IOException) {
            null
        }
    }

    override fun setVoice(newVoiceName: String) {

        currentVoiceFolder = if (voices.contains(newVoiceName)) {
            newVoiceName
        } else {
            NONE
        }

        onChangeVoice()
    }

    private fun onChangeVoice() {
        GlobalScope.launch {
            _onChangeVoiceFlow.emit(currentVoiceFolder)
        }
    }

    override fun setVoiceSpeed(speedInPercent: Int) {
        currentVoiceSpeed = speedInPercent
        onChangeVoiceSpeed()
    }

    private fun onChangeVoiceSpeed() {
        GlobalScope.launch {
            _onChangeVoiceSpeedFlow.emit(currentVoiceSpeed)
        }
    }


    private fun getSoundFileName(taskId: String): String {
        if (currentVoiceFolder == NONE) {
            return NONE
        }
        return "$currentVoiceFolder/${taskId + SOUND_FILE_TYPE}"
    }

    companion object Companion {
        private const val SOUNDS_FOLDER = "sounds"
        private const val SOUND_FILE_TYPE = ".mp3"
        private const val DEFAULT_VOICE_SPEED = 100 // in percent
        private const val MIN_VOICE_SPEED = 50 // in percent
        private const val MAX_VOICE_SPEED = 200 // in percent
        const val SOUND_TEST = "2x2="
        private const val NONE = ""
    }
}