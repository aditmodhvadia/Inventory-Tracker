package com.fazemeright.myinventorytracker.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.domain.models.Result
import com.fazemeright.myinventorytracker.ui.base.BaseViewModel
import com.fazemeright.myinventorytracker.usecase.LogInUserWithEmailPasswordUseCase
import com.fazemeright.myinventorytracker.usecase.LogInUserWithTokenUseCase
import com.fazemeright.myinventorytracker.utils.Validator.isEmailValid
import com.fazemeright.myinventorytracker.utils.Validator.isPasswordValid
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.security.InvalidParameterException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val app: Application,
    private val logInUserWithEmailPassword: LogInUserWithEmailPasswordUseCase,
    private val logInUserWithToken: LogInUserWithTokenUseCase
) : BaseViewModel(app) {

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
                    logInUserWithEmailPassword(email, password)
                }
            }
        }
    }

    fun signInWithToken(idToken: String) {
        viewModelScope.launch {
            _loginResult.value = logInUserWithToken(idToken)
        }
    }
}
