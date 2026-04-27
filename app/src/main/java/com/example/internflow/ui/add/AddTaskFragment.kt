package com.example.internflow.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.internflow.R
import com.example.internflow.databinding.FragmentAddTaskBinding
import com.example.internflow.ui.common.TaskConstants
import com.example.internflow.ui.common.taskViewModels
import com.google.android.material.snackbar.Snackbar

class AddTaskFragment : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private val viewModel by taskViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupPickers()
        binding.saveButton.setOnClickListener { saveTask() }
    }

    private fun setupPickers() = with(binding) {
        val statusAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            TaskConstants.statuses
        )
        val difficultyAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            TaskConstants.difficulties
        )

        statusInput.setAdapter(statusAdapter)
        difficultyInput.setAdapter(difficultyAdapter)
        statusInput.setText(TaskConstants.STATUS_NOT_STARTED, false)
        difficultyInput.setText(TaskConstants.DIFFICULTY_MEDIUM, false)
    }

    private fun saveTask() = with(binding) {
        val title = titleInput.text?.toString().orEmpty()
        if (title.isBlank()) {
            titleLayout.error = getString(R.string.add_task_title_error)
            return
        }
        titleLayout.error = null

        viewModel.addTask(
            title = title,
            status = statusInput.text.toString(),
            difficulty = difficultyInput.text.toString()
        ) {
            Snackbar.make(root, R.string.add_task_saved, Snackbar.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
