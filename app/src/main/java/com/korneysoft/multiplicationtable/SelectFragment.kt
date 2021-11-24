package com.korneysoft.multiplicationtable

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.korneysoft.multiplicationtable.databinding.FragmentSelectBinding

class SelectFragment : Fragment(R.layout.fragment_select) {

    private var isStudyMode:Boolean=true
    private lateinit var binding: FragmentSelectBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentSelectBinding.bind(view)
        isStudyMode= requireArguments().getBoolean(ARG_MODE_ISSTUDY)
    }

    companion object {
        const val ARG_MODE_ISSTUDY="isStudy"

    }
}
