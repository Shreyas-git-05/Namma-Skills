package com.nammaskill.app.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nammaskill.app.data.FakeData
import com.nammaskill.app.data.prefs.UserPrefs
import kotlinx.coroutines.flow.first

/**
 * Demo "auto sync":
 * - Reads favorite trades from DataStore
 * - If any are set, fires a local notification about an upcoming batch
 *
 * This works fully offline (no Firebase/API keys) so the project runs cleanly in Android Studio.
 */
class SyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        NotificationUtils.ensureChannel(applicationContext)

        val profile = UserPrefs(applicationContext).profileFlow.first()
        val favs = profile.favoriteTrades
        if (favs.isEmpty()) return Result.success()

        val course = FakeData.courses.firstOrNull { it.trade in favs } ?: return Result.success()
        val center = FakeData.centers.firstOrNull { it.id == course.centerId }

        val title = "New batch: ${course.trade}"
        val message = "Starts ${course.startDate} • ${center?.name ?: "Skill Center"} • Duration ${course.durationMonths} months"
        NotificationUtils.showCourseAlert(applicationContext, title, message)

        return Result.success()
    }
}

