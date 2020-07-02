package com.fazemeright.myinventorytracker.firebase.api

import com.fazemeright.myinventorytracker.firebase.models.Failure
import com.fazemeright.myinventorytracker.firebase.models.Result
import com.fazemeright.myinventorytracker.firebase.models.Success
import com.fazemeright.myinventorytracker.utils.Validator.isEmailValid
import com.fazemeright.myinventorytracker.utils.Validator.isPasswordValid
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * @author Adit Modhvadia
 */
object FireBaseApiManager : FireBaseApiWrapper() {

    suspend fun signInWithEmailPassword(email: String, password: String): Result<FirebaseUser> {
        require(email.isEmailValid()) {
            "Email address is invalid"
        }

        require(email.isPasswordValid()) {
            "Password is invalid"
        }
        return withContext(Dispatchers.IO) {
            try {
                val result = signIn(email, password).await()
                if (result.user != null) Success(data = result.user!!) else Failure(msg = "Some error occurred")
            } catch (e: Exception) {
                Timber.e(e)
                Failure(msg = "")
            }
        }
    }

    suspend fun registerWithEmailPassword(email: String, password: String): Result<FirebaseUser> {
        require(email.isEmailValid()) {
            "Email address is invalid"
        }

        require(email.isPasswordValid()) {
            "Password is invalid"
        }
        return withContext(Dispatchers.IO) {
            try {
                val result = register(email, password).await()
                if (result.user != null) Success(data = result.user!!) else Failure(msg = "Some error occurred")
            } catch (e: Exception) {
                Timber.e(e)
                Failure(msg = "")
            }
        }
    }

    fun isUserSignedIn(): Result<Boolean> {
        return if (userSignedIn()) {
            Success(msg = "User is singed in", data = true)
        } else {
            Failure(msg = "User is not signed in")
        }
    }
}