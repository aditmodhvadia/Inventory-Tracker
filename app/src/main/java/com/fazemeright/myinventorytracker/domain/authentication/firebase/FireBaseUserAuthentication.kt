package com.fazemeright.myinventorytracker.domain.authentication.firebase

import com.fazemeright.myinventorytracker.domain.authentication.UserAuthentication
import com.fazemeright.myinventorytracker.domain.models.Result
import com.fazemeright.myinventorytracker.utils.Validator.isEmailValid
import com.fazemeright.myinventorytracker.utils.Validator.isPasswordValid
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object FireBaseUserAuthentication : UserAuthentication {
    override suspend fun signIn(
        email: String,
        password: String
    ): Result<AuthResult> {
        require(email.isEmailValid()) {
            "Email address is invalid"
        }
        require(email.isPasswordValid()) {
            "Password is invalid"
        }
        return withContext(Dispatchers.IO) {
            try {
                val task = Firebase.auth.signInWithEmailAndPassword(email, password).await()
                Result.Success(task)
            } catch (e: Exception) {
                Result.Error(e, "Authentication Failed")
            }
        }
    }

    override suspend fun signIn(idToken: String): Result<AuthResult> {
        return withContext(Dispatchers.IO) {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)

                val task = Firebase.auth.signInWithCredential(credential).await()
                Result.Success(task)
            } catch (e: Exception) {
                Result.Error(e, "Sign In Failed")
            }
        }
    }

    override suspend fun register(email: String, password: String): Result<AuthResult> {
        require(email.isEmailValid()) {
            "Email address is invalid"
        }
        require(email.isPasswordValid()) {
            "Password is invalid"
        }
        return withContext(Dispatchers.IO) {
            try {
//                AuthenticationResultAdapterForFirebaseAuthResult(
                Firebase.auth.createUserWithEmailAndPassword(
                    email,
                    password
                ).await()
                    /*)*/.let {
                        Result.Success(it)
                    }
            } catch (e: Exception) {
                Result.Error(e, "New User registration failed")
            }
        }
    }

    override fun logout() {
        Firebase.auth.signOut()
    }

    override fun currentUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

    override fun isUserSignedIn(): Boolean {
        return currentUser() != null
    }

    override suspend fun sendPasswordResetEmail(): Result<Void> {
        return withContext(Dispatchers.IO) {
            try {
                currentUser()?.sendEmailVerification()?.await()?.let {
                    Result.Success(it)
                }
                    ?: throw java.lang.Exception("Failed to send email verification: No user signed in!")
            } catch (e: Exception) {
                Result.Error(e, "Send Password reset email failed")
            }
        }
    }

    override fun isUserVerified(): Boolean {
        return currentUser()?.isEmailVerified ?: false
    }

    override fun getCurrentUserUUID(): String? {
        return currentUser()?.uid
    }
}
