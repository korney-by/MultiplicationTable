package com.korneysoft.multiplicationtable

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.korneysoft.multiplicationtable.databinding.FragmentStatiscicBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticFragment: Fragment(R.layout.fragment_statiscic) {
    private lateinit var binding: FragmentStatiscicBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentStatiscicBinding.bind(view)
    }
}