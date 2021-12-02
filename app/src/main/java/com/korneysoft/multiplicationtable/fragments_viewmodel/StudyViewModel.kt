package com.korneysoft.multiplicationtable.fragments_viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.domain.usecases.PlaySoundUserCase
import com.korneysoft.multiplicationtable.player.Speaker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG="T7-StudyViewModel"

@HiltViewModel
class StudyViewModel @Inject constructor(private val soundRepository: SoundRepository) :
    ViewModel() {

    init{
        Log.d(TAG,"soundRepository - ${soundRepository.toString()}")
    }

    fun study(number:Int){
        val useCase = PlaySoundUserCase(soundRepository)
        useCase.execute("3x5")
    }

}
