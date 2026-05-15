package com.nammaskill.app.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "applications")
data class ApplicationEntity(
    @PrimaryKey val id: String,
    val courseId: String,
    val courseTrade: String,
    val centerName: String,
    val createdAtEpochMs: Long,
)

