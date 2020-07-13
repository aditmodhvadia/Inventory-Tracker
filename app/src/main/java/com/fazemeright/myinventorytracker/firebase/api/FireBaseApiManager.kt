package com.fazemeright.myinventorytracker.firebase.api

import com.fazemeright.myinventorytracker.firebase.models.Result
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
                if (result.user != null) Result.Success(data = result.user!!) else Result.Error(msg = "Some error occurred")
            } catch (e: Exception) {
                Timber.e(e)
                Result.Error(msg = "")
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
                if (result.user != null) Result.Success(data = result.user!!) else Result.Error(msg = "Some error occurred")
            } catch (e: Exception) {
                Timber.e(e)
                Result.Error(msg = "")
            }
        }
    }

    fun isUserSignedIn(): Result<Boolean> {
        return if (userSignedIn()) {
            Result.Success(msg = "User is singed in", data = true)
        } else {
            Result.Error(msg = "User is not signed in")
        }
    }
}