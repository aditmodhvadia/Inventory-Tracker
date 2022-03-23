package com.fazemeright.myinventorytracker.domain.authentication

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

/**
 * @author Adit Modhvadia
 * @since 2.1.1
 */
interface UserAuthentication {

    /**
     * Sign in user with [email] and [password]
     *
     * @param email user email
     * @param password  user password
     *
     * @return [Result] with result of [AuthenticationResult]
     */
    suspend fun signIn(email: String, password: String): Result<AuthResult>

    /**
     * Sign in user with [idToken]
     *
     * @param idToken id token from federated sign in
     *
     * @return [Result] with result of [AuthenticationResult]
     */
    suspend fun signIn(idToken: String): Result<AuthResult>

    /**
     * Register user with [email] and [password]
     *
     * @param email user email
     * @param password  user password
     *
     * @return [Result] with result of [AuthenticationResult]
     */
    suspend fun register(email: String, password: String): Result<AuthResult>

    /**
     * Logout user
     */
    fun logout()

    /**
     * Current logged in user
     *
     * @return current [FirebaseUser]
     */
    fun currentUser(): FirebaseUser?

    /**
     * Determine if a user is logged in
     *
     * @return <code>true</code> if user is logged in, else <code>false</code>
     */
    fun isUserSignedIn(): Boolean

    suspend fun sendPasswordResetEmail(): Result<Void>?

    /**
     * Determine if a user has verified email
     *
     * @return <code>true</code> if user verified email, else <code>false</code>
     */
    fun isUserVerified(): Boolean

    /**
     * Get current logged in user's unique identifying id
     *
     * @return user's uuid
     */
    fun getCurrentUserUUID(): String?
}
