package com.fazemeright.myinventorytracker.ui.login

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.domain.models.Result
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import com.fazemeright.myinventorytracker.ui.base.BaseViewModel
import com.fazemeright.myinventorytracker.utils.Validator.isEmailValid
import com.fazemeright.myinventorytracker.utils.Validator.isPasswordValid
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.launch
import java.security.InvalidParameterException

class LoginViewModel @ViewModelInject constructor(
    @ActivityContext private val context: Context, private val repository: InventoryRepository
) : BaseViewModel(context, repository) {

    private val _loginResult = MutableLiveData<Result<FirebaseUser>>()

    val loginResult: LiveData<Result<FirebaseUser>>
        get() = _loginResult

    fun onLoginClicked(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = when {
                !email.isEmailValid() -> Result.Error(
                    exception = InvalidParameterException("Invalid Email"),
                    msg = getString(R.string.invalid_email_msg)
                )

                !password.isPasswordValid() -> Result.Error(
                    exception = InvalidParameterException("Invalid Password"),
                    msg = getString(R.string.invalid_password_msg)
                )
                else -> {
                    repository.performLogin(email, password)
                }
            }
        }
    }

    fun signInWithToken(idToken: String) {
        viewModelScope.launch {
            _loginResult.value = repository.signInWithToken(idToken)
        }
    }
}