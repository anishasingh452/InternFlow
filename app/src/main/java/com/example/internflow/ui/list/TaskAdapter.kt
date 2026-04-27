package com.example.internflow.ui.list

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.internflow.R
import com.example.internflow.databinding.ItemTaskBinding
import com.example.internflow.ui.common.TaskConstants
import com.example.internflow.ui.common.TaskUiModel

class TaskAdapter : ListAdapter<TaskUiModel, TaskAdapter.TaskViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: TaskUiModel) = with(binding) {
            val context = root.context
            val task = model.task
            titleText.text = task.title
            statusChip.text = task.status
            difficultyText.text = task.difficulty
            timeText.text = model.relativeTime
            attentionBadge.visibility = if (model.needsAttention) View.VISIBLE else View.GONE

            val statusColor = when (task.status) {
                TaskConstants.STATUS_IN_PROGRESS -> R.color.status_in_progress
                TaskConstants.STATUS_DONE -> R.color.status_done
                else -> R.color.status_not_started
            }
            statusChip.chipStrokeColor = ColorStateList.valueOf(
                ContextCompat.getColor(context, statusColor)
            )
            statusChip.setTextColor(ContextCompat.getColor(context, statusColor))
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<TaskUiModel>() {
        override fun areItemsTheSame(oldItem: TaskUiModel, newItem: TaskUiModel): Boolean {
            return oldItem.task.id == newItem.task.id
        }

        override fun areContentsTheSame(oldItem: TaskUiModel, newItem: TaskUiModel): Boolean {
            return oldItem == newItem
        }
    }
}
