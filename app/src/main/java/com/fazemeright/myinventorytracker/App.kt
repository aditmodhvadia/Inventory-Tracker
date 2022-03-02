package com.fazemeright.myinventorytracker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

/**
 * Android Application entry class.
 *
 * @author Adit Modhvadia
 * @since 2.1.1
 */
@HiltAndroidApp
class App @Inject constructor() : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        handleTheme(PreferenceManager.getDefaultSharedPreferences(this))

        setUpTimber()
    }

    /**
     * Set up [Timber.DebugTree] when [BuildConfig] is [BuildConfig.DEBUG].
     */
    private fun setUpTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    @Inject
    lateinit var workManagerConfig: Configuration

    override fun getWorkManagerConfiguration() = workManagerConfig

    /**
     * Handle the theme of the app from the user settings stored in [SharedPreferences].
     */
    private fun handleTheme(preferences: SharedPreferences?) {
        val mode = when (preferences?.getString("theme", "system_default")) {
            "light" -> AppCompatDelegate.MODE_NIGHT_NO
            "dark" -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}
