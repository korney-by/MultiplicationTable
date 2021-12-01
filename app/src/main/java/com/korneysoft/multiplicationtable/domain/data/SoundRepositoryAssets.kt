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
    override val currentVoice: String get() = currentVoiceFolder
    override val voices: List<String>

    private val _onChangeVoiceFlow = MutableSharedFlow<String>()
    override val onChangeVoiceFlow = _onChangeVoiceFlow.asSharedFlow()
    override var onChangeVoice: (() -> Unit)? = null

//    private val _commandToPlayerFlow = MutableSharedFlow<String>()
//    val commandToPlayerFlow = _commandToPlayerFlow.asSharedFlow()

    private var currentVoiceFolder: String = NONE

    init {
        voices = readVoices()
        defaultVoice = voices[0]
        setCurrentVoice(defaultVoice)
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

    override fun setCurrentVoice(newVoiceName: String) {


        currentVoiceFolder = if (voices.contains(newVoiceName)) {
            newVoiceName
        } else {
            NONE
        }
        onChangeVoice?.invoke()
        GlobalScope.launch {
            _onChangeVoiceFlow.emit(newVoiceName)
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
        const val SOUND_TEST = "2x2="
        private const val NONE = ""
    }
}
