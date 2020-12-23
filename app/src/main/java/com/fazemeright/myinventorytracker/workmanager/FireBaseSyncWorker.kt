package com.fazemeright.myinventorytracker.workmanager

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class FireBaseSyncWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: InventoryRepository
) : CoroutineWorker(
    context,
    workerParams
) {

    override val coroutineContext: CoroutineDispatcher
        get() = Dispatchers.IO

    override suspend fun doWork(): Result = coroutineScope {
        Timber.d("Worker called")
        val result = async { repository.syncLocalAndCloud() }
        result.await()

        Result.success()
    }
}