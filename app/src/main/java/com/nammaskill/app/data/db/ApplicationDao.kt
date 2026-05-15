package com.nammaskill.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(application: ApplicationEntity)

    @Query("SELECT * FROM applications ORDER BY createdAtEpochMs DESC")
    fun observeAll(): Flow<List<ApplicationEntity>>
}

