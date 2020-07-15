package com.fazemeright.myinventorytracker.ui.splash

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
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

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        viewModel.isUserSignedIn.observe(this, Observer { userIsSignedIn ->
            if (userIsSignedIn) {
                open(ItemListActivity::class.java)
            } else {
                open(LoginActivity::class.java)
            }
            finish()
        })
    }

    override fun getLayoutId(): Int = R.layout.activity_splash


}
