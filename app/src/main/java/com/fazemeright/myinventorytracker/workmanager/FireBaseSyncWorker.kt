package com.fazemeright.myinventorytracker.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltWorker
class FireBaseSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: InventoryRepository
) : CoroutineWorker(
    context,
    workerParams
) {

    override suspend fun doWork(): Result = coroutineScope {
        withContext(Dispatchers.IO) {
            Timber.d("Worker called")
            val result = async { repository.syncLocalAndCloud() }
            result.await()

            Result.success()
        }
    }
}