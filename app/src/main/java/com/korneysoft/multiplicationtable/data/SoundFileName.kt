package com.korneysoft.multiplicationtable.data

class SoundFileName private constructor() {

    companion object {
        private const val SOUND_FILE_TYPE = "mp3"

        fun make(
            multiplier1: Int,
            multiplier2: Int,
            isHasResult: Boolean = false
        ): String {
            val stringResult = if (isHasResult) "=" else ""
            if (multiplier1 <= multiplier2) {
                return "${multiplier1}x$multiplier2$stringResult.$SOUND_FILE_TYPE"
            } else {
                return "${multiplier2}x$multiplier1$stringResult.$SOUND_FILE_TYPE"
            }
        }
    }
}