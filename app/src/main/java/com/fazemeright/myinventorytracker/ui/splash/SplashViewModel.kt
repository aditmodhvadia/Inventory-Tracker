package com.fazemeright.myinventorytracker.ui.splash

import android.app.Application
import android.content.pm.PackageManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.work.*
import com.fazemeright.myinventorytracker.domain.models.Result
import com.fazemeright.myinventorytracker.usecase.IsUserSignedInUseCase
import com.fazemeright.myinventorytracker.workmanager.FireBaseSyncWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val _isUserSignedIn: IsUserSignedInUseCase,
) : ViewModel() {
    /*@Inject
    lateinit var _isUserSignedIn: IsUserSignedInUseCase
*/
    val isUserSignedIn: LiveData<Boolean> =
        liveData {
            delay(DELAY)
            when (_isUserSignedIn()) {
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
                /*val pInfo: PackageInfo =
                    app.packageManager.getPackageInfo(app.packageName, 0)
                pInfo.versionName*/
                ""
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                "Debug Version"
            }
        }

    fun syncLocalAndCloudData(applicationContext: Application) {
        val workManager = WorkManager.getInstance(applicationContext)
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


    companion object {
        const val DELAY = 400L
    }
}
