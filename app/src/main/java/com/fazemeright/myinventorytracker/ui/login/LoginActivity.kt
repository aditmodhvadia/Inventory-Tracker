package com.fazemeright.myinventorytracker.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.databinding.ActivityLoginBinding
import com.fazemeright.myinventorytracker.firebase.models.Result
import com.fazemeright.myinventorytracker.ui.base.BaseActivity
import com.fazemeright.myinventorytracker.ui.itemlist.ItemListActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewmodel = viewModel

        binding.lifecycleOwner = this

        binding.btnLogin.setOnClickListener {
//            TODO: Show Loading
            hideKeyboard()
            viewModel.onLoginClicked(
                binding.etEmail.text.toString().trim(),
                binding.etPassword.text.toString().trim()
            )
        }

        viewModel.loginResult.observe(this, Observer { result ->
//            TODO: Hide Loading
            if (result is Result.Success) {
                open(ItemListActivity::class.java)
                finish()
            } else if (result is Result.Error) {
                Timber.e(result.exception)
                showToast(result.msg)
            }
        })
    }

    override fun getLayoutId(): Int = R.layout.activity_login
}