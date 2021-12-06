package com.korneysoft.multiplicationtable.fragments.study

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.korneysoft.multiplicationtable.databinding.StudyItemBinding
import com.korneysoft.multiplicationtable.domain.entities.Task

class StudyTaskListAdapter : ListAdapter<Task, StudyTaskViewHolder>(itemComparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyTaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = StudyItemBinding.inflate(layoutInflater, parent, false)
        return StudyTaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudyTaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        private val itemComparator = object : DiffUtil.ItemCallback<Task>() {

            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.equals(newItem)
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.equals(newItem)
            }
        }
    }

}