package com.korneysoft.multiplicationtable.ui.fragments.statistic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.korneysoft.multiplicationtable.R
import com.korneysoft.multiplicationtable.databinding.StatisticItemBinding
import com.korneysoft.multiplicationtable.domain.entities.TaskRating
import com.korneysoft.multiplicationtable.domain.usecases.statistic.TaskWithRating

class StatisticTableAdapter :
    ListAdapter<TaskWithRating, StatisticTableAdapter.StatisticTableHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticTableHolder {
        return StatisticTableHolder(
            StatisticItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: StatisticTableHolder, position: Int) {
        val taskWithRating = getItem(position)
        holder.bind(taskWithRating)
    }

    inner class StatisticTableHolder(
        private val binding: StatisticItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(taskWithRating: TaskWithRating) {
            var backgroundId: Int

            if (taskWithRating.id.isEmpty()) {
                backgroundId = R.drawable.cell_of_table_blue
                if (taskWithRating.result > 0) {
                    binding.textValue.text = taskWithRating.result.toString()
                }
            } else {
                binding.textValue.text = taskWithRating.result.toString()
                backgroundId = when (taskWithRating.rating) {
                    TaskRating.NOT_STUDIED -> R.drawable.cell_of_table_gray
                    TaskRating.POORLY_STUDIED -> R.drawable.cell_of_table_red
                    TaskRating.MIDDLE_STUDIED -> R.drawable.cell_of_table_yellow
                    TaskRating.GOOD_STUDIED -> R.drawable.cell_of_table_green
                }
            }
            binding.viewCell.setBackgroundResource(backgroundId)
        }
    }

    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<TaskWithRating>() {
            override fun areItemsTheSame(
                oldItem: TaskWithRating,
                newItem: TaskWithRating
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: TaskWithRating,
                newItem: TaskWithRating
            ): Boolean {
                return oldItem.equals(newItem)
            }
        }
    }
}
