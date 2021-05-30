package com.fazemeright.myinventorytracker.ui.main

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.databinding.ActivityMainBinding
import com.fazemeright.myinventorytracker.ui.base.BaseActivity
import com.google.android.material.appbar.CollapsingToolbarLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.splashFragment,
                R.id.loginFragment,
                R.id.itemListFragment
            ),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout).isTitleEnabled = false
        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)
        /*findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
            .setupWithNavController(
                findViewById(R.id.toolbar),
                navController,
                appBarConfiguration
            )*/
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }
}