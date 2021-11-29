package com.korneysoft.multiplicationtable.player

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.widget.Toast

class Speaker private constructor() {

    companion object {
        private val mediaPlayer = MediaPlayer()

        fun play(fileDescriptor: AssetFileDescriptor) {
            fileDescriptor.apply {
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
    }
}