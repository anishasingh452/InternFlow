package com.example.internflow.ui.common

import android.text.format.DateUtils
import com.example.internflow.data.Task

private const val ATTENTION_THRESHOLD_MS = 24 * 60 * 60 * 1000L

data class TaskUiModel(
    val task: Task,
    val relativeTime: String,
    val needsAttention: Boolean
)

data class DashboardStats(
    val total: Int = 0,
    val inProgress: Int = 0,
    val completed: Int = 0,
    val needsAttention: Int = 0
)

fun Task.toUiModel(now: Long = System.currentTimeMillis()): TaskUiModel {
    val isStaleInProgress = status == TaskConstants.STATUS_IN_PROGRESS &&
        now - timestamp > ATTENTION_THRESHOLD_MS
    val relative = DateUtils.getRelativeTimeSpanString(
        timestamp,
        now,
        DateUtils.MINUTE_IN_MILLIS
    ).toString()

    return TaskUiModel(
        task = this,
        relativeTime = relative,
        needsAttention = isStaleInProgress
    )
}
