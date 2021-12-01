package com.korneysoft.multiplicationtable.fragments_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.domain.usecases.PlaySoundUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val soundRepository: SoundRepository) :
    ViewModel() {

    val onChangeVoiceF = soundRepository.onChangeVoiceFlow

    init {
        soundRepository.onChangeVoice = {
            playTestSound()
        }

        lunchCollectChangeVoiceFlow()
    }

    override fun onCleared() {
        soundRepository.onChangeVoice=null
        super.onCleared()
    }

    private fun lunchCollectChangeVoiceFlow() {
        viewModelScope.launch {
            onChangeVoiceF.collect {
                playTestSound()
            }
        }
    }

     fun playTestSound() {
        val useCase = PlaySoundUserCase(soundRepository)
        useCase.execute(soundRepository.testSoundFileId)
    }
}
