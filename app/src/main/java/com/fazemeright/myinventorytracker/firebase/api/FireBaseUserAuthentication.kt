package com.fazemeright.myinventorytracker.firebase.api

import com.fazemeright.myinventorytracker.firebase.interfaces.UserAuthentication
import com.fazemeright.myinventorytracker.utils.Validator.isEmailValid
import com.fazemeright.myinventorytracker.utils.Validator.isPasswordValid
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FireBaseUserAuthentication : UserAuthentication {
    override suspend fun signIn(email: String, password: String): Task<AuthResult> {
        require(email.isEmailValid()) {
            "Email address is invalid"
        }
        require(email.isPasswordValid()) {
            "Password is invalid"
        }
        return withContext(Dispatchers.IO) {
            Firebase.auth.signInWithEmailAndPassword(email, password)
        }
    }

    override suspend fun signIn(idToken: String): Task<AuthResult> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return Firebase.auth.signInWithCredential(credential)
    }

    override suspend fun register(email: String, password: String): Task<AuthResult> {
        require(email.isEmailValid()) {
            "Email address is invalid"
        }
        require(email.isPasswordValid()) {
            "Password is invalid"
        }
        return withContext(Dispatchers.IO) {
            Firebase.auth.createUserWithEmailAndPassword(email, password)
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

    override suspend fun sendPasswordResetEmail(): Task<Void>? {
        return currentUser()?.sendEmailVerification()
    }

    override fun isUserVerified(): Boolean {
        return currentUser()?.isEmailVerified ?: false
    }

    override fun getCurrentUserUUID(): String? {
        return currentUser()?.uid
    }
}