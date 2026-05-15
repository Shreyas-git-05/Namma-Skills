package com.nammaskill.app.ui.courses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.nammaskill.app.data.CourseBatch
import com.nammaskill.app.data.DurationType
import com.nammaskill.app.data.FakeData
import com.nammaskill.app.databinding.ItemCourseBinding

class CoursesAdapter(
    private val onApply: (CourseBatch) -> Unit,
    private val onInterested: (CourseBatch) -> Unit,
) : ListAdapter<CourseBatch, CoursesAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<CourseBatch>() {
        override fun areItemsTheSame(oldItem: CourseBatch, newItem: CourseBatch) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CourseBatch, newItem: CourseBatch) = oldItem == newItem
    }

    class VH(val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        val center = FakeData.centers.firstOrNull { it.id == item.centerId }

        holder.binding.courseTitle.text = "${item.trade} (${item.durationMonths} months)"
        holder.binding.courseMeta.text =
            "Starts: ${item.startDate} • Center: ${center?.name ?: "Skill Center"} • Job Guarantee: ${if (item.jobGuarantee) "Yes" else "No"}"
        holder.binding.courseEligibility.text = "Eligibility: ${item.eligibility}"

        // Badges

        holder.binding.btnApply.setOnClickListener { onApply(item) }
        holder.binding.btnInterested.setOnClickListener { onInterested(item) }
    }

    private fun makeBadge(holder: VH, text: String): Chip {
        return Chip(holder.binding.root.context).apply {
            this.text = text
            isClickable = false
            isCheckable = false
            setEnsureMinTouchTargetSize(false)
        }
    }
}
