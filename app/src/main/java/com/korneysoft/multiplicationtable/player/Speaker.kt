package com.korneysoft.multiplicationtable.player

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.os.Build


object Speaker {
    fun play(fileDescriptor: AssetFileDescriptor, speedInPercent: Int = 100) {
        var playbackParams: PlaybackParams

        val mediaPlayer = MediaPlayer()

        try {
            mediaPlayer.setDataSource(
                fileDescriptor.fileDescriptor,
                fileDescriptor.startOffset,
                fileDescriptor.length
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                playbackParams = PlaybackParams()
                playbackParams.speed = speedInPercent.toFloat() / 100
                mediaPlayer.playbackParams=playbackParams
            }

            mediaPlayer.prepare()
            mediaPlayer.start()

        } catch (ex: Exception) {
            //Toast.makeText(context, ex.message, Toast.LENGTH_LONG).show()
        }
    }
}
