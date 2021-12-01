package com.korneysoft.multiplicationtable.data

import android.content.res.AssetFileDescriptor

interface SoundRepository {
    fun getFileDescriptor(taskId: String): AssetFileDescriptor?
    fun setCurrentVoice(voiceName: String)
}
