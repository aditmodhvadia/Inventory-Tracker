package com.fazemeright.myinventorytracker.ui.base

import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.work.*
import com.fazemeright.myinventorytracker.App
import com.fazemeright.myinventorytracker.workmanager.FireBaseSyncWorker
import dagger.hilt.android.qualifiers.ActivityContext
import java.util.concurrent.TimeUnit

abstract class BaseViewModel constructor(
//    @ActivityContext private val context: Context,
    @ActivityContext private val app: App
) :
    AndroidViewModel(app) {

    fun getString(@StringRes resId: Int): String {
        return app.getString(resId)
    }

    fun syncLocalAndCloudData() {
        val workManager = WorkManager.getInstance(app)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresDeviceIdle(true)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<FireBaseSyncWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

//        Instantly sync
        val oneTimeRequest = OneTimeWorkRequest.from(FireBaseSyncWorker::class.java)
        workManager.enqueue(oneTimeRequest)
//        Set a periodic repeating work request to sync data daily once
        workManager.enqueue(syncRequest)
    }
}
