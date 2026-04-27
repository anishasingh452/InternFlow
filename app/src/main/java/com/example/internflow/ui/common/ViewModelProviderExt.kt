package com.example.internflow.ui.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.internflow.InternFlowApplication

fun Fragment.taskViewModels() = activityViewModels<TaskViewModel> {
    val app = requireActivity().application as InternFlowApplication
    TaskViewModelFactory(app.repository)
}
