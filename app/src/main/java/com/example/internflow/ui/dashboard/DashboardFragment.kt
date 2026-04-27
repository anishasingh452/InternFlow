package com.example.internflow.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.internflow.R
import com.example.internflow.databinding.FragmentDashboardBinding
import com.example.internflow.ui.common.DashboardStats
import com.example.internflow.ui.common.taskViewModels
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel by taskViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addTaskButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addTaskFragment)
        }
        binding.viewTasksButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_taskListFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stats.collect(::renderStats)
            }
        }
    }

    private fun renderStats(stats: DashboardStats) = with(binding) {
        totalCard.statLabel.text = getString(R.string.dashboard_total_tasks)
        totalCard.statValue.text = stats.total.toString()
        progressCard.statLabel.text = getString(R.string.dashboard_in_progress)
        progressCard.statValue.text = stats.inProgress.toString()
        completedCard.statLabel.text = getString(R.string.dashboard_completed)
        completedCard.statValue.text = stats.completed.toString()
        attentionCard.statLabel.text = getString(R.string.dashboard_needs_attention)
        attentionCard.statValue.text = stats.needsAttention.toString()
        attentionSummary.text = if (stats.needsAttention == 0) {
            getString(R.string.dashboard_attention_clear)
        } else {
            resources.getQuantityString(
                R.plurals.dashboard_attention_count,
                stats.needsAttention,
                stats.needsAttention
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
