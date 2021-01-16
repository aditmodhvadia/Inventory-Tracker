package com.fazemeright.myinventorytracker.ui.splash

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.fazemeright.myinventorytracker.domain.models.Result
import com.fazemeright.myinventorytracker.ui.base.BaseViewModel
import com.fazemeright.myinventorytracker.usecase.IsUserSignedInUseCase
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.delay
import timber.log.Timber


class SplashViewModel @ViewModelInject constructor(
    @ActivityContext private val context: Context,
    private val _isUserSignedIn: IsUserSignedInUseCase
) : BaseViewModel(context) {

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