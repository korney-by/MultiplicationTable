package com.korneysoft.multiplicationtable.application

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.korneysoft.multiplicationtable.R
import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.domain.entities.TaskRating
import com.korneysoft.multiplicationtable.domain.usecases.statistic.GetStatisticListUseCase
import com.korneysoft.multiplicationtable.domain.usecases.statistic.TaskWithRating
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext val context: Context,
    private val soundRepository: SoundRepository,
    private val getStatisticListUseCase: GetStatisticListUseCase
) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val statisticKey: String get() = context.getString(R.string.key_statistic)

        fun loadSoundRepositorySettings() {
        val currVoice = preferences.getString(
            context.getString(R.string.key_voice),
            soundRepository.defaultVoice
        )
        currVoice?.let { soundRepository.setVoice(currVoice) }

        val voiceSpeed = preferences.getInt(
            context.getString(R.string.key_voice_speed),
            soundRepository.VOICE_SPEED_DEFAULT
        )
        soundRepository.setVoiceSpeed(voiceSpeed)
    }

    private fun getStatisticKeyRating(taskWithRating: TaskWithRating): String {
        return "{$statisticKey}_${taskWithRating.id}"
    }

    fun readStatistic(): List<TaskWithRating> {
        val statisticList = getStatisticListUseCase()
        for (i in statisticList.indices) {
            val taskWithRating = statisticList[i]
            val ratingName = preferences.getString(
                getStatisticKeyRating(taskWithRating),
                TaskRating.NOT_STUDIED.name
            )
            ratingName?.let {
                taskWithRating.rating = TaskRating.valueOf(ratingName)
            }
        }
        return statisticList
    }

    fun saveStatistic(): Boolean {
        val statisticList = getStatisticListUseCase()
        val editor: SharedPreferences.Editor = preferences.edit()

        for (i in statisticList.indices) {
            val taskWithRating = statisticList[i]
            editor.putString(getStatisticKeyRating(taskWithRating), taskWithRating.rating.name)
        }
        return editor.commit()
    }

}
