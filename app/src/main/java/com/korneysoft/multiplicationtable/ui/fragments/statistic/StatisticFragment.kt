package com.korneysoft.multiplicationtable.ui.fragments.statistic

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.korneysoft.multiplicationtable.R
import com.korneysoft.multiplicationtable.databinding.FragmentStatiscicBinding
import com.korneysoft.multiplicationtable.ui.fragments.settings.SettingsViewModel

class StatisticFragment : Fragment(R.layout.fragment_statiscic) {

    private val viewModel by viewModels<StatisticViewModel>()
    private lateinit var binding: FragmentStatiscicBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatiscicBinding.bind(view)

        binding.recyclerStatisticTable.apply {
            layoutManager = GridLayoutManager(context, viewModel.columnCount)
            adapter =StatisticTableAdapter()
        }


    }
}
