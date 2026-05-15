package com.nammaskill.app

import android.app.Application
import com.nammaskill.app.sync.NotificationUtils
import com.nammaskill.app.sync.SyncScheduler

class NammaSkillApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationUtils.ensureChannel(this)
        SyncScheduler.schedule(this)
    }
}

