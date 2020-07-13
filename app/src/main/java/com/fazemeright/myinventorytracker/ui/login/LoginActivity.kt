package com.fazemeright.myinventorytracker.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.databinding.ActivityLoginBinding
import com.fazemeright.myinventorytracker.firebase.models.Result
import com.fazemeright.myinventorytracker.ui.itemlist.ItemListActivity

class LoginActivity : AppCompatActivity() {
    val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.viewmodel = viewModel

        binding.lifecycleOwner = this

        binding.btnLogin.setOnClickListener {
//            TODO: Show Loading
            viewModel.onLoginClicked(
                binding.etEmail.text.toString().trim(),
                binding.etPassword.text.toString().trim()
            )
        }

        viewModel.loginResult.observe(this, Observer { result ->
//            TODO: Hide Loading
            if (result is Result.Success) {
                open(ItemListActivity::class.java)
            } else if (result is Result.Error) {
                showToast(result.msg)
            }
        })
    }

    //    TODO: Move to BaseActivity
    private fun Context.showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    //    TODO: Replace AppCompatActivity with baseActivity after adding Base to all the activities
    private fun AppCompatActivity.open(java: Class<out AppCompatActivity>) {
        startActivity(Intent(this, java))
    }
}