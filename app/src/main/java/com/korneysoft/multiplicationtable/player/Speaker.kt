package com.korneysoft.multiplicationtable.player

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer

object Speaker {
    fun play(fileDescriptor: AssetFileDescriptor) {
        val mediaPlayer = MediaPlayer()
        try {
            mediaPlayer.setDataSource(
                fileDescriptor.fileDescriptor,
                fileDescriptor.startOffset,
                fileDescriptor.length
            )
            mediaPlayer.prepare()
            mediaPlayer.start()

        } catch (ex: Exception) {
            //Toast.makeText(context, ex.message, Toast.LENGTH_LONG).show()
        }
    }
}
