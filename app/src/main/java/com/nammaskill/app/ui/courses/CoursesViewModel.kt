package com.nammaskill.app.ui.courses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nammaskill.app.data.CourseBatch
import com.nammaskill.app.data.DurationType
import com.nammaskill.app.data.FakeData

class CoursesViewModel : ViewModel() {

    private val allCourses: List<CourseBatch> = FakeData.courses

    private val selectedTrade = MutableLiveData<String?>(null)
    private val selectedDuration = MutableLiveData<DurationType?>(null)
    private val queryText = MutableLiveData<String?>(null)

    private val _courses = MutableLiveData<List<CourseBatch>>(allCourses)
    val courses: LiveData<List<CourseBatch>> = _courses

    fun setTrade(trade: String?) {
        selectedTrade.value = trade
        applyFilters()
    }

    fun setDuration(duration: DurationType?) {
        selectedDuration.value = duration
        applyFilters()
    }

    fun setQuery(query: String?) {
        queryText.value = query
        applyFilters()
    }

    fun refresh() {
        // In real app: pull latest from Firebase. Here we keep sample data.
        applyFilters()
    }

    private fun applyFilters() {
        val trade = selectedTrade.value
        val duration = selectedDuration.value
        val q = queryText.value?.trim().orEmpty()

        _courses.value = allCourses
            .filter { c ->
                val okTrade = trade == null || c.trade == trade
                val okDur = duration == null || c.durationType == duration
                val okQuery =
                    q.isBlank() ||
                        c.trade.contains(q, ignoreCase = true) ||
                        c.eligibility.contains(q, ignoreCase = true) ||
                        c.startDate.contains(q, ignoreCase = true)
                okTrade && okDur && okQuery
            }
            .sortedBy { it.startDate } // ISO date string sorts correctly
    }
}
