package com.fazemeright.myinventorytracker.firebase.interfaces

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface UserAuthentication {

    suspend fun signIn(email: String, password: String): Task<AuthResult>

    suspend fun signIn(idToken: String): Task<AuthResult>

    suspend fun register(email: String, password: String): Task<AuthResult>

    fun logout()

    fun currentUser(): FirebaseUser?

    fun isUserSignedIn(): Boolean

    suspend fun sendPasswordResetEmail(): Task<Void>?

    fun isUserVerified(): Boolean

    fun getCurrentUserUUID(): String?
}