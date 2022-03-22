package com.fazemeright.myinventorytracker.domain.authentication

import com.fazemeright.myinventorytracker.domain.models.User

interface AuthenticationResult {

    fun getUser(): User
}