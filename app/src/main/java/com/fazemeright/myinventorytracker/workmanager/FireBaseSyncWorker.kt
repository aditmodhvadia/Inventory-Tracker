package com.fazemeright.myinventorytracker.workmanager

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.fazemeright.myinventorytracker.data.InventoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

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

        val result = async { repository.syncLocalAndCloud() }
        result.await()

        Result.success()
    }
}