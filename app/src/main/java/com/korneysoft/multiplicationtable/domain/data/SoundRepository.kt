package com.korneysoft.multiplicationtable.domain.data

import android.content.res.AssetFileDescriptor
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Singleton

@Singleton
interface SoundRepository {
    val defaultVoice: String
    val currentVoice: String
    val onChangeVoiceFlow: SharedFlow<String>
    var onChangeVoice: (() -> Unit)?
    val testSoundFileId: String
    val voices: List<String>
    val NONE: String

    fun getSoundFileDescriptor(taskId: String): AssetFileDescriptor?
    fun setCurrentVoice(voiceName: String)
}
