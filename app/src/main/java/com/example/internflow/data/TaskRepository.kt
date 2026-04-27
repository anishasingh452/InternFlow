package com.example.internflow.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val tasks: Flow<List<Task>> = taskDao.observeAll()

    suspend fun addTask(title: String, status: String, difficulty: String) {
        taskDao.insert(
            Task(
                title = title.trim(),
                status = status,
                difficulty = difficulty,
                timestamp = System.currentTimeMillis()
            )
        )
    }
}
