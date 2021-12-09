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

    val voices: List<String> get() = getVoicesUseCase.execute()
    //val defaultVoice get() = getDefaultVoiceUseCase.execute()
    val voice get() = getVoiceUseCase.execute()
    val VOICE_SPEED_MAX get() = getVoiceSpeedMaxUseCase.execute()
    val VOICE_SPEED_MIN get() = getVoiceSpeedMinUseCase.execute()

    private val _voiceSpeedStateFlow = MutableStateFlow<Int>(getVoiceSpeedUseCase.execute())
    val voiceSpeedStateFlow = _voiceSpeedStateFlow.asStateFlow()

    private fun lunchEmitVoiceSpeedStateFlow() {
        viewModelScope.launch {
            _voiceSpeedStateFlow.emit(getVoiceSpeedUseCase.execute())
        }
    }

    fun setVoice(voice: String) {
        setVoiceUseCase.execute(voice)
        playTestSound()
    }

    fun setVoiceSpeed(speed: Int) {
        setVoiceSpeedUseCase.execute(speed)
        playTestSound()
        lunchEmitVoiceSpeedStateFlow()
    }

    fun playTestSound() {
        playTestSoundUseCase.execute()
    }

}
