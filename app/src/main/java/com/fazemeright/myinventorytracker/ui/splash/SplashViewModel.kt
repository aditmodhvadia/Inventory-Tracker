package com.fazemeright.myinventorytracker.ui.splash

import android.app.Application
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.fazemeright.myinventorytracker.domain.models.Result
import com.fazemeright.myinventorytracker.ui.base.BaseViewModel
import com.fazemeright.myinventorytracker.usecase.IsUserSignedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val app: Application,
    private val _isUserSignedIn: IsUserSignedInUseCase
) : BaseViewModel(app) {

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
                    app.packageManager.getPackageInfo(app.packageName, 0)
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
