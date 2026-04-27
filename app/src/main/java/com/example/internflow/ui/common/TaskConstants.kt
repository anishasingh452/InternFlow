package com.example.internflow.ui.common

object TaskConstants {
    const val STATUS_NOT_STARTED = "Not Started"
    const val STATUS_IN_PROGRESS = "In Progress"
    const val STATUS_DONE = "Done"

    const val DIFFICULTY_EASY = "Easy"
    const val DIFFICULTY_MEDIUM = "Medium"
    const val DIFFICULTY_HARD = "Hard"

    val statuses = listOf(STATUS_NOT_STARTED, STATUS_IN_PROGRESS, STATUS_DONE)
    val difficulties = listOf(DIFFICULTY_EASY, DIFFICULTY_MEDIUM, DIFFICULTY_HARD)
}
