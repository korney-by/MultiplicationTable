package com.korneysoft.multiplicationtable.domain.data.implementation

import android.content.Context
import android.content.res.AssetFileDescriptor
import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundRepositoryAssets @Inject constructor(@ApplicationContext val appContext: Context) :
    SoundRepository {

    override val none = NONE
    override val testSoundFileId: String = SOUND_TEST
    override val defaultVoice: String
    override val voiceName: String get() = currentVoiceFolder
    override val voiceSpeed: Int get() = currentVoiceSpeed
    override val voiceSpeedDefault: Int = DEFAULT_VOICE_SPEED
    override val voiceSpeedMin = MIN_VOICE_SPEED
    override val voiceSpeedMax = MAX_VOICE_SPEED
    override val voices: List<String>
    override val repeatSoundFileId: String = SOUND_REPEAT
    override val helloSoundFileId: String = SOUND_HELLO
    override val rightSoundFileId: String = SOUND_RIGHT
    override val errorSoundFileId: String = SOUND_ERROR

    private var currentVoiceFolder: String = none
    private var currentVoiceSpeed: Int = DEFAULT_VOICE_SPEED

    init {
        voices = readVoices()
        defaultVoice = voices[0]
        setVoice(defaultVoice)
    }

    override fun getStudyBySoundFileId(number: Int): String {
        return SOUND_LEARN_BY + number
    }

    private fun readVoices(): List<String> {
        val fileList = appContext.assets.list(SOUNDS_FOLDER)
        val voiceList = mutableListOf<String>()

        // API 21 (maybe not only) returns extra files, so need to check
        fileList?.forEach {
            if (it.startsWith(SOUNDS_FOLDER_PREFIX)) {
                voiceList.add(it)
            }
        }
        return voiceList
    }

    override fun getSoundFileDescriptor(taskId: String): AssetFileDescriptor? {
        val fileName = getSoundFileName(taskId)
        if (fileName == none) return null

        return try {
            appContext.assets.openFd("$SOUNDS_FOLDER/$fileName")
        } catch (e: IOException) {
            null
        }
    }

    override fun setVoice(voiceName: String) {
        if (voices.contains(voiceName)) {
            currentVoiceFolder = voiceName
        }
    }

    override fun setVoiceSpeed(speedInPercent: Int) {
        currentVoiceSpeed = speedInPercent
    }

    private fun getSoundFileName(taskId: String): String {
        if (currentVoiceFolder == none) {
            return none
        }
        return "$currentVoiceFolder/${taskId + SOUND_FILE_TYPE}"
    }

    companion object Companion {
        private const val SOUNDS_FOLDER = "sounds"
        private const val SOUNDS_FOLDER_PREFIX = "voice_"
        private const val SOUND_FILE_TYPE = ".mp3"
        private const val DEFAULT_VOICE_SPEED = 100 // in percent
        private const val MIN_VOICE_SPEED = 50 // in percent
        private const val MAX_VOICE_SPEED = 200 // in percent
        private const val SOUND_TEST = "2x2="
        private const val SOUND_REPEAT = "repeat"
        private const val SOUND_HELLO = "hello"
        private const val SOUND_RIGHT = "right"
        private const val SOUND_LEARN_BY = "by"
        private const val SOUND_ERROR = "no"
        private const val NONE = ""
    }
}
