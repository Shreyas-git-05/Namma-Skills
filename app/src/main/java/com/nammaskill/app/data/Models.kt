package com.nammaskill.app.data

enum class DurationType { SHORT_TERM, LONG_TERM }

data class SkillCenter(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val lat: Double,
    val lng: Double,
)

data class CourseBatch(
    val id: String,
    val trade: String,
    val centerId: String,
    /** ISO-like date string, e.g. "2026-06-10" */
    val startDate: String,
    val durationMonths: Int,
    val durationType: DurationType,
    val eligibility: String,
    val jobGuarantee: Boolean,
)

data class SuccessStory(
    val id: String,
    val title: String,
    val body: String,
)
