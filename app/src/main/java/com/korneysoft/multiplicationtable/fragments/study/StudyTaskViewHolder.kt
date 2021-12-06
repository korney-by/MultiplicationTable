package com.korneysoft.multiplicationtable.fragments.study

import androidx.recyclerview.widget.RecyclerView
import com.korneysoft.multiplicationtable.databinding.StudyItemBinding
import com.korneysoft.multiplicationtable.domain.entities.Task

class StudyTaskViewHolder(
    private val binding: StudyItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: Task){
        binding.textTask.text=task.toStringWithResult()
    }
}