package com.fazemeright.myinventorytracker.ui.login

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import com.fazemeright.myinventorytracker.firebase.models.Result
import com.fazemeright.myinventorytracker.ui.base.BaseViewModel
import com.fazemeright.myinventorytracker.utils.Validator.isEmailValid
import com.fazemeright.myinventorytracker.utils.Validator.isPasswordValid
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.launch
import java.security.InvalidParameterException

class LoginViewModel @ViewModelInject constructor(
    private val repository: InventoryRepository, @ActivityContext private val context: Context
) : BaseViewModel(context, repository) {

    private val _loginResult = MutableLiveData<Result<FirebaseUser>>()

    val loginResult: LiveData<Result<FirebaseUser>>
        get() = _loginResult

    fun onLoginClicked(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = when {
                !email.isEmailValid() -> Result.Error(
                    exception = InvalidParameterException("Invalid Email"),
                    msg = "Please enter valid email address"
                )

                !password.isPasswordValid() -> Result.Error(
                    exception = InvalidParameterException("Invalid Password"),
                    msg = "Password should be greater than 6 characters"
                )
                else -> {
                    repository.performLogin(email, password)
                }
            }
        }
    }
}