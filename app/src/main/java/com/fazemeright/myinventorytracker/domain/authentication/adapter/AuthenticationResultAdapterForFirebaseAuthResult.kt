package com.fazemeright.myinventorytracker.domain.authentication.adapter

import com.fazemeright.myinventorytracker.domain.authentication.AuthenticationResult
import com.google.firebase.auth.AuthResult

class AuthenticationResultAdapterForFirebaseAuthResult(val authResult: AuthResult) :
    AuthenticationResult {
}