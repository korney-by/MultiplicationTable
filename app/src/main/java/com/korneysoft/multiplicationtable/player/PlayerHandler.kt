package com.korneysoft.multiplicationtable.player

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.os.Build
import android.util.Log

object PlayerHandler {
    val mediaPlayer = MediaPlayer()

    fun play(fileDescriptor: AssetFileDescriptor, speedInPercent:Int) {
        val playbackParams: PlaybackParams

        mediaPlayer.reset()

        try {
            mediaPlayer.setDataSource(
                fileDescriptor.fileDescriptor,
                fileDescriptor.startOffset,
                fileDescriptor.length
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                playbackParams = PlaybackParams()
                playbackParams.speed = speedInPercentToPlaybackSpeed(speedInPercent)
                mediaPlayer.playbackParams=playbackParams
            }

            mediaPlayer.prepare()
            mediaPlayer.start()

        } catch (ex: Exception) {
            //Toast.makeText(context, ex.message, Toast.LENGTH_LONG).show()
            Log.d("T7-Player","${ex.message}")
        }
    }

    private fun speedInPercentToPlaybackSpeed(percent:Int):Float{
        return percent.toFloat() / 100
    }
}
