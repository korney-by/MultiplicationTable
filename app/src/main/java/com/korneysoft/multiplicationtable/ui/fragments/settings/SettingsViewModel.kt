package com.korneysoft.multiplicationtable.ui.fragments.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korneysoft.multiplicationtable.domain.usecases.voice.GetVoiceSpeedMaxUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.GetVoiceSpeedMinUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.GetVoiceSpeedUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.GetVoiceUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.GetVoicesUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.PlayTestSoundUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.SetVoiceSpeedUseCase
import com.korneysoft.multiplicationtable.domain.usecases.voice.SetVoiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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
    val voice get() = getVoiceUseCase()
    val voiceSpeedMax get() = getVoiceSpeedMaxUseCase()
    val voiceSpeedMin get() = getVoiceSpeedMinUseCase()

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

    private fun playTestSound() {
        playTestSoundUseCase()
    }
}
