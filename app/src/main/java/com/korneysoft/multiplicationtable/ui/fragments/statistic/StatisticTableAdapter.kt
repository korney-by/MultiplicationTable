package com.korneysoft.multiplicationtable.ui.fragments.statistic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.korneysoft.multiplicationtable.databinding.StatisticItemBinding
import com.korneysoft.multiplicationtable.domain.entities.Task

class StatisticTableAdapter :
    ListAdapter<Task, StatisticTableAdapter.StatisticTableHolder>(itemComparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticTableHolder {
        return StatisticTableHolder(
            StatisticItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: StatisticTableHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }

    inner class StatisticTableHolder(
        private val binding: StatisticItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
        }
    }

    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.getId()==newItem.getId()
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.equals(newItem)
            }


        }
    }
}