package com.fazemeright.myinventorytracker.firebase.api

import com.fazemeright.myinventorytracker.utils.Validator.isEmailValid
import com.fazemeright.myinventorytracker.utils.Validator.isPasswordValid
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Adit Modhvadia
 */
object FireBaseApiManager : FireBaseApiWrapper() {

    suspend fun signInWithEmailPassword(email: String, password: String): Task<AuthResult> {
        require(email.isEmailValid()) {
            "Email address is invalid"
        }

        require(email.isPasswordValid()) {
            "Password is invalid"
        }
        return withContext(Dispatchers.IO) {
            signIn(email, password)
        }
    }

    suspend fun registerWithEmailPassword(email: String, password: String): Task<AuthResult> {
        require(email.isEmailValid()) {
            "Email address is invalid"
        }

        require(email.isPasswordValid()) {
            "Password is invalid"
        }
        return withContext(Dispatchers.IO) {
            register(email, password)
        }
    }

    fun isUserSignedIn(): Boolean = userSignedIn()
}