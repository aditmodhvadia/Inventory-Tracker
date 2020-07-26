package com.fazemeright.myinventorytracker.ui.splash

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.liveData
import androidx.work.*
import com.fazemeright.myinventorytracker.data.InventoryRepository
import com.fazemeright.myinventorytracker.firebase.models.Result
import com.fazemeright.myinventorytracker.ui.base.BaseViewModel
import com.fazemeright.myinventorytracker.workmanager.FireBaseSyncWorker
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.delay
import timber.log.Timber
import java.util.concurrent.TimeUnit


class SplashViewModel @ViewModelInject constructor(
    @ActivityContext private val context: Context, private val repository: InventoryRepository
) : BaseViewModel(context) {

    fun enqueueSyncWork() {
        val workManager = WorkManager.getInstance(context)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresDeviceIdle(true)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<FireBaseSyncWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

//        val singleRequest = OneTimeWorkRequestBuilder<FireBaseSyncWorker>()
//            .build()
//        Instantly sync
        workManager.enqueue(OneTimeWorkRequest.from(FireBaseSyncWorker::class.java))
//        Set a periodic repeating work request to sync data daily once
        workManager.enqueue(syncRequest)
    }

    val isUserSignedIn =
        liveData {
            delay(DELAY)
            when (repository.isUserSignedIn()) {
                is Result.Success -> {
                    emit(true)
                }
                is Result.Error -> {
                    emit(false)
                }
            }
        }

    val versionName: String
        get() {
            return try {
                Timber.d("Version name info called")
                val pInfo: PackageInfo =
                    context.packageManager.getPackageInfo(context.packageName, 0)
                pInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                "Debug Version"
            }
        }

    companion object {
        const val DELAY = 400L
    }
}