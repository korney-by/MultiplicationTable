package com.korneysoft.multiplicationtable.fragments_viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.domain.entities.Task
import com.korneysoft.multiplicationtable.domain.usecases.GetStudyListUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlaySoundUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "T7-StudyViewModel"

@HiltViewModel
class StudyViewModel @Inject constructor(private val playSoundUseCase: PlaySoundUseCase) :
    ViewModel() {

    var studyList = listOf<Task>()

    fun setNumberToStudy(number: Int) {
        val getStudyListUseCase = GetStudyListUseCase()
        studyList = getStudyListUseCase.execute(number)
    }

    fun startStudy(number: Int) {
        val useCase = playSoundUseCase
        useCase.execute("3x5")
    }

}
