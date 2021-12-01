package com.korneysoft.multiplicationtable.fragments_viewmodel

import androidx.lifecycle.ViewModel
import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.player.Speaker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudyViewModel @Inject constructor(private val soundRepository: SoundRepository) :
    ViewModel() {

    fun study(number:Int){
        val fileDescriptor=soundRepository.getSoundFileDescriptor("3x5")
        fileDescriptor?.let{
            Speaker.play(fileDescriptor)
        }
    }
}
