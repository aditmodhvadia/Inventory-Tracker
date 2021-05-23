package com.fazemeright.myinventorytracker.ui.splash

import android.os.Bundle
import androidx.activity.viewModels
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.databinding.ActivitySplashBinding
import com.fazemeright.myinventorytracker.ui.base.BaseActivity
import com.fazemeright.myinventorytracker.ui.itemlist.ItemListActivity
import com.fazemeright.myinventorytracker.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        viewModel.isUserSignedIn.observe(
            this,
            { userIsSignedIn ->
                if (userIsSignedIn) {
                    viewModel.syncLocalAndCloudData()
                    open(ItemListActivity::class.java)
                } else {
                    open(LoginActivity::class.java)
                }
                finish()
            }
        )
    }

    override fun getLayoutId(): Int = R.layout.activity_splash
}
