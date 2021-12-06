package com.korneysoft.multiplicationtable.domain.data

import android.content.res.AssetFileDescriptor
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Singleton

interface SoundRepository {
    val defaultVoice: String
    val voiceName: String
    val voiceSpeed: Int
    val VOICE_SPEED_DEFAULT: Int
    val VOICE_SPEED_MIN: Int
    val VOICE_SPEED_MAX: Int
    val testSoundFileId: String
    val repeatSoundFileId: String
    val helloSoundFileId: String
    val rightSoundFileId: String
    val voices: List<String>
    val NONE: String

    fun getSoundFileDescriptor(taskId: String): AssetFileDescriptor?
    fun setVoice(voiceName: String)
    fun setVoiceSpeed(speedInPercent: Int)
    fun getLearnBySoundFileId(number: Int): String
}
