package com.example.internflow

import android.app.Application
import com.example.internflow.data.AppDatabase
import com.example.internflow.data.TaskRepository

class InternFlowApplication : Application() {
    private val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { TaskRepository(database.taskDao()) }
}
