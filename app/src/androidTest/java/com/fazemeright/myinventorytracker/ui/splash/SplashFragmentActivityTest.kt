package com.fazemeright.myinventorytracker.ui.splash

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.isViewDisplayed
import com.fazemeright.myinventorytracker.ui.base.BaseUiFragmentTest
import timber.log.Timber

class SplashFragmentActivityTest : BaseUiFragmentTest<SplashFragment>() {

    override fun allViewsAreDisplayed() {
        R.id.tvAppVersion.isViewDisplayed()
        R.id.tvAppTagLine.isViewDisplayed()
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        val expected = try {
            Timber.d("Version name info called")
            val pInfo: PackageInfo =
                appContext.packageManager.getPackageInfo(appContext.packageName, 0)
            pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            "Debug Version"
        }
        onView(withId(R.id.tvAppVersion)).check(
            matches(
                withText(expected)
            )
        )
    }

    override fun getFragment(): Class<SplashFragment> = SplashFragment::class.java
}
