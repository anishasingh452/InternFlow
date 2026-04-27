package com.example.internflow.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.internflow.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    val tasks: StateFlow<List<TaskUiModel>> = repository.tasks
        .map { tasks -> tasks.map { it.toUiModel() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val stats: StateFlow<DashboardStats> = tasks
        .map { taskModels ->
            DashboardStats(
                total = taskModels.size,
                inProgress = taskModels.count { it.task.status == TaskConstants.STATUS_IN_PROGRESS },
                completed = taskModels.count { it.task.status == TaskConstants.STATUS_DONE },
                needsAttention = taskModels.count { it.needsAttention }
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DashboardStats())

    fun addTask(title: String, status: String, difficulty: String, onSaved: () -> Unit) {
        if (title.isBlank()) return

        viewModelScope.launch {
            repository.addTask(title, status, difficulty)
            onSaved()
        }
    }
}

class TaskViewModelFactory(
    private val repository: TaskRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
