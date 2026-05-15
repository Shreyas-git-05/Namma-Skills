package com.nammaskill.app.ui.courses

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.nammaskill.app.R
import com.nammaskill.app.data.CourseBatch
import com.nammaskill.app.data.DurationType
import com.nammaskill.app.data.FakeData
import com.nammaskill.app.data.db.AppDatabase
import com.nammaskill.app.data.db.ApplicationEntity
import com.nammaskill.app.data.prefs.UserPrefs
import com.nammaskill.app.databinding.FragmentCoursesBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID

class CoursesFragment : Fragment() {

    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!

    private val vm: CoursesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CoursesAdapter(
            onApply = { course -> showCandidateSummaryAndApply(course) },
            onInterested = { course -> interestPing(course) }
        )
        binding.coursesRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.coursesRecycler.adapter = adapter

        setupTradeChips()
        setupDurationChips()

        binding.etSearch.doAfterTextChanged { text ->
            vm.setQuery(text?.toString())
        }

        binding.swipeRefresh.setOnRefreshListener {
            vm.refresh()
            binding.swipeRefresh.isRefreshing = false
        }

        vm.courses.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    private fun setupTradeChips() {
        binding.tradeChipGroup.removeAllViews()

        val all = makeChip("All")
        all.isChecked = true
        all.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) vm.setTrade(null)
        }
        binding.tradeChipGroup.addView(all)

        FakeData.trades.forEach { trade ->
            val chip = makeChip(trade)
            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) vm.setTrade(trade)
            }
            binding.tradeChipGroup.addView(chip)
        }
    }

    private fun setupDurationChips() {
        binding.durationChipGroup.removeAllViews()

        val all = makeChip("All")
        all.isChecked = true
        all.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) vm.setDuration(null)
        }
        binding.durationChipGroup.addView(all)

        val shortChip = makeChip(getString(R.string.short_term))
        shortChip.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) vm.setDuration(DurationType.SHORT_TERM)
        }
        binding.durationChipGroup.addView(shortChip)

        val longChip = makeChip(getString(R.string.long_term))
        longChip.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) vm.setDuration(DurationType.LONG_TERM)
        }
        binding.durationChipGroup.addView(longChip)
    }

    private fun makeChip(text: String): Chip =
        Chip(requireContext()).apply {
            this.text = text
            isCheckable = true
            isClickable = true
            setEnsureMinTouchTargetSize(true)
        }

    private fun showCandidateSummaryAndApply(course: CourseBatch) {
        lifecycleScope.launch {
            val prefs = UserPrefs(requireContext())
            val profile = prefs.profileFlow.first()

            if (profile.name.isBlank() || profile.phone.isBlank()) {
                Snackbar.make(binding.root, "Please complete your Profile to apply in one click.", Snackbar.LENGTH_LONG)
                    .setAction("Open") { findNavController().navigate(R.id.profileFragment) }
                    .show()
                return@launch
            }

            val center = FakeData.centers.firstOrNull { it.id == course.centerId }
            val summary = buildString {
                appendLine("Candidate Summary")
                appendLine("Name: ${profile.name}")
                appendLine("Phone: ${profile.phone}")
                appendLine("Village: ${profile.village}")
                appendLine()
                appendLine("Course Applied")
                appendLine("Trade: ${course.trade}")
                appendLine("Start Date: ${course.startDate}")
                appendLine("Duration: ${course.durationMonths} months (${if (course.durationType == DurationType.SHORT_TERM) "Short term" else "Long term"})")
                appendLine("Eligibility: ${course.eligibility}")
                appendLine("Job Guarantee: ${if (course.jobGuarantee) "Yes" else "No"}")
                appendLine("Center: ${center?.name ?: "Skill Center"}")
                appendLine("Center Phone: ${center?.phone ?: "-"}")
            }

            AlertDialog.Builder(requireContext())
                .setTitle("Candidate Summary")
                .setMessage(summary)
                .setNegativeButton(getString(R.string.share_summary)) { _, _ ->
                    shareText(summary)
                }
                .setPositiveButton(getString(R.string.confirm_apply)) { _, _ ->
                    saveApplication(course)
                    Toast.makeText(requireContext(), "Applied! Your profile was shared with the center.", Toast.LENGTH_LONG).show()
                }
                .show()
        }
    }

    private fun shareText(text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(Intent.createChooser(intent, "Share via"))
    }

    private fun saveApplication(course: CourseBatch) {
        lifecycleScope.launch {
            val center = FakeData.centers.firstOrNull { it.id == course.centerId }
            val db = AppDatabase.get(requireContext())
            db.applicationDao().upsert(
                ApplicationEntity(
                    id = UUID.randomUUID().toString(),
                    courseId = course.id,
                    courseTrade = course.trade,
                    centerName = center?.name ?: "Skill Center",
                    createdAtEpochMs = System.currentTimeMillis()
                )
            )
        }
    }

    private fun interestPing(course: CourseBatch) {
        val center = FakeData.centers.firstOrNull { it.id == course.centerId } ?: return
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${center.phone}")
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
