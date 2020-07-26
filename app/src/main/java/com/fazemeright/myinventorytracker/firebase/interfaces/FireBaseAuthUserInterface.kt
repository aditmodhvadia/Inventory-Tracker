package com.fazemeright.myinventorytracker.firebase.interfaces

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface FireBaseAuthUserInterface {

    fun signIn(email: String, password: String): Task<AuthResult>

    fun signIn(idToken: String): Task<AuthResult>

    fun register(email: String, password: String): Task<AuthResult>

    fun logout()

    fun currentUser(): FirebaseUser?

    fun userSignedIn(): Boolean

    fun sendPasswordResetEmail(): Task<Void>?

    fun isUserVerified(): Boolean

    fun getCurrentUserUUID(): String?
}