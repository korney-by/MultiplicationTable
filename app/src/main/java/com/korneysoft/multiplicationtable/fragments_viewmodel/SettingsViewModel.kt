package com.korneysoft.multiplicationtable.fragments_viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.domain.usecases.PlaySoundUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "T7-SettingsViewModel"

@HiltViewModel
class SettingsViewModel @Inject constructor(private val soundRepository: SoundRepository) :
    ViewModel() {

    val voices by lazy { soundRepository.voices }
    val defaultVoice by lazy { soundRepository.defaultVoice }
    val VOICE_SPEED_MAX by lazy { soundRepository.VOICE_SPEED_MAX }
    val VOICE_SPEED_MIN by lazy { soundRepository.VOICE_SPEED_MIN }
    val VOICE_SPEED_DEFAULT by lazy { soundRepository.VOICE_SPEED_DEFAULT }

    private val _voiceSpeedStateFlow by lazy{ MutableStateFlow<Int>(soundRepository.voiceSpeed)}
    val voiceSpeedStateFlow: StateFlow<Int> =_voiceSpeedStateFlow

    init {
        Log.d(TAG, "soundRepository - ${soundRepository.toString()}")
        lunchCollectChangeVoiceFlow()
        lunchCollectChangeVoiceSpeedFlow()
    }

    private fun lunchEmitVoiceSpeedStateFlow(){
        viewModelScope.launch {
            _voiceSpeedStateFlow.emit(soundRepository.voiceSpeed)
        }
    }

    private fun lunchCollectChangeVoiceFlow() {
        viewModelScope.launch {
            soundRepository.onChangeVoiceFlow.collect {
                playTestSound()
            }
        }
    }

    private fun lunchCollectChangeVoiceSpeedFlow() {
        viewModelScope.launch {
            soundRepository.onChangeVoiceSpeedFlow.collect {
                playTestSound()
            }
        }
    }

    fun setVoice(voice: String) {
        soundRepository.setVoice(voice)
    }

    fun setVoiceSpeed(speed: Int) {
        soundRepository.setVoiceSpeed(speed)
        lunchEmitVoiceSpeedStateFlow()
    }

    fun playTestSound() {
        val useCase = PlaySoundUserCase(soundRepository)
        useCase.execute(soundRepository.testSoundFileId)
    }

}
