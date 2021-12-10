package com.korneysoft.multiplicationtable.domain.data

import android.content.res.AssetFileDescriptor

interface SoundRepository {
    val defaultVoice: String
    val voiceName: String
    val voiceSpeed: Int
    val voiceSpeedDefault: Int
    val voiceSpeedMin: Int
    val voiceSpeedMax: Int
    val testSoundFileId: String
    val repeatSoundFileId: String
    val helloSoundFileId: String
    val rightSoundFileId: String
    val errorSoundFileId: String
    val voices: List<String>
    val none: String

    fun getSoundFileDescriptor(taskId: String): AssetFileDescriptor?
    fun setVoice(voiceName: String)
    fun setVoiceSpeed(speedInPercent: Int)
    fun getStudyBySoundFileId(number: Int): String
}
