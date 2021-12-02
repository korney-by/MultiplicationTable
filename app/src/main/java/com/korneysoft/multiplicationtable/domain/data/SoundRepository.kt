package com.korneysoft.multiplicationtable.domain.data

import android.content.res.AssetFileDescriptor
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Singleton

interface SoundRepository {
    val defaultVoice: String
    val voice: String
    val defaultVoiceSpeed: Int
    val voiceSpeed: Int
    val onChangeVoiceFlow: SharedFlow<String>
    val onChangeVoiceSpeedFlow: SharedFlow<Int>
    val testSoundFileId: String
    val voices: List<String>
    val NONE: String

    fun getSoundFileDescriptor(taskId: String): AssetFileDescriptor?
    fun setCurrentVoice(voiceName: String)
    fun setVoiceSpeed(speedInPercent: Int)
}
