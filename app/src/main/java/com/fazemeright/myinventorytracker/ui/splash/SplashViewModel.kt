package com.fazemeright.myinventorytracker.ui.splash

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.liveData
import com.fazemeright.myinventorytracker.data.InventoryRepository
import com.fazemeright.myinventorytracker.firebase.models.Result
import com.fazemeright.myinventorytracker.ui.base.BaseViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.delay
import timber.log.Timber


class SplashViewModel @ViewModelInject constructor(
    @ActivityContext private val context: Context, private val repository: InventoryRepository
) : BaseViewModel(context) {
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