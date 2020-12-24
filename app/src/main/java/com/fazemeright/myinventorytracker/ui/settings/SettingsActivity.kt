package com.fazemeright.myinventorytracker.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.preference.PreferenceFragmentCompat
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.databinding.SettingsActivityBinding
import com.fazemeright.myinventorytracker.ui.base.BaseActivity
import kotlinx.android.synthetic.main.collapsing_toolbar.*
import timber.log.Timber

class SettingsActivity : BaseActivity<SettingsActivityBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }

        override fun onResume() {
            super.onResume()
            preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences?,
            key: String?
        ) {
            Timber.d(key)
            when (key) {
                "theme" -> {
                    val themeMode = when (sharedPreferences?.getString(key, "system_default")) {
                        "light" -> MODE_NIGHT_NO
                        "dark" -> MODE_NIGHT_YES
                        else -> MODE_NIGHT_FOLLOW_SYSTEM
                    }
                    setDefaultNightMode(themeMode)
                }
                "sync" -> {
                    //                    TODO: Set WorkManager to sync local with firebase
                }
            }
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun getLayoutId(): Int = R.layout.settings_activity
}