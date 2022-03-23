package com.fazemeright.myinventorytracker.domain.authentication.firebase

import com.fazemeright.myinventorytracker.domain.authentication.UserAuthentication
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
                Result.success(task)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun signIn(idToken: String): Result<AuthResult> {
        return withContext(Dispatchers.IO) {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)

                val task = Firebase.auth.signInWithCredential(credential).await()
                Result.success(task)
            } catch (e: Exception) {
                Result.failure(e)
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
                        Result.success(it)
                    }
            } catch (e: Exception) {
                Result.failure(e)
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
                    Result.success(it)
                }
                    ?: throw java.lang.Exception("Failed to send email verification: No user signed in!")
            } catch (e: Exception) {
                Result.failure(e)
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
