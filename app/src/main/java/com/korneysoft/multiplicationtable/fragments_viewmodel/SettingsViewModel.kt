package com.korneysoft.multiplicationtable.fragments_viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.domain.usecases.PlaySoundUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG="T7-SettingsViewModel"

@HiltViewModel
class SettingsViewModel @Inject constructor(private val soundRepository: SoundRepository) :
    ViewModel() {

    val onChangeVoiceFlow = soundRepository.onChangeVoiceFlow
    val onChangeVoiceSpeedFlow = soundRepository.onChangeVoiceSpeedFlow

    init {
        Log.d(TAG,"soundRepository - ${soundRepository.toString()}")
    }

     fun playTestSound() {
        val useCase = PlaySoundUserCase(soundRepository)
        useCase.execute(soundRepository.testSoundFileId)
    }
}
