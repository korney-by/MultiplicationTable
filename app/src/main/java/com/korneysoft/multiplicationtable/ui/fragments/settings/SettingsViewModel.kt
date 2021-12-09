package com.korneysoft.multiplicationtable.ui.fragments.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korneysoft.multiplicationtable.domain.usecases.voice.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "T7-SettingsViewModel"

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val playTestSoundUseCase: PlayTestSoundUseCase,
    private val getVoiceSpeedMaxUseCase: GetVoiceSpeedMaxUseCase,
    private val getVoiceSpeedMinUseCase: GetVoiceSpeedMinUseCase,
    private val getVoiceSpeedUseCase: GetVoiceSpeedUseCase,
    private val setVoiceSpeedUseCase: SetVoiceSpeedUseCase,
    private val getVoicesUseCase: GetVoicesUseCase,
    private val setVoiceUseCase: SetVoiceUseCase,
    private val getVoiceUseCase: GetVoiceUseCase
) : ViewModel() {

    val voices: List<String> get() = getVoicesUseCase()
    //val defaultVoice get() = getDefaultVoiceUseCase()
    val voice get() = getVoiceUseCase()
    val VOICE_SPEED_MAX get() = getVoiceSpeedMaxUseCase()
    val VOICE_SPEED_MIN get() = getVoiceSpeedMinUseCase()

    private val _voiceSpeedStateFlow = MutableStateFlow<Int>(getVoiceSpeedUseCase())
    val voiceSpeedStateFlow = _voiceSpeedStateFlow.asStateFlow()

    private fun lunchEmitVoiceSpeedStateFlow() {
        viewModelScope.launch {
            _voiceSpeedStateFlow.emit(getVoiceSpeedUseCase())
        }
    }

    fun setVoice(voice: String) {
        setVoiceUseCase(voice)
        playTestSound()
    }

    fun setVoiceSpeed(speed: Int) {
        setVoiceSpeedUseCase(speed)
        playTestSound()
        lunchEmitVoiceSpeedStateFlow()
    }

    fun playTestSound() {
        playTestSoundUseCase()
    }

}
