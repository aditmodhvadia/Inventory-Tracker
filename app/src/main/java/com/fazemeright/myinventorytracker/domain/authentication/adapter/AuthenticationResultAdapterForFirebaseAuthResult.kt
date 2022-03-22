package com.fazemeright.myinventorytracker.domain.authentication.adapter

import com.fazemeright.myinventorytracker.domain.authentication.AuthenticationResult
import com.fazemeright.myinventorytracker.domain.models.User
import com.google.firebase.auth.AuthResult

class AuthenticationResultAdapterForFirebaseAuthResult(private val authResult: AuthResult) :
    AuthenticationResult {
    override fun getUser(): User {
        return UserAdapterForFirebaseUser(authResult.user!!)
    }
}