package com.korneysoft.multiplicationtable

import com.korneysoft.multiplicationtable.data.SoundRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
@HiltViewModel
class StudyViewModel @Inject constructor(private val soundRepository: SoundRepository){

}