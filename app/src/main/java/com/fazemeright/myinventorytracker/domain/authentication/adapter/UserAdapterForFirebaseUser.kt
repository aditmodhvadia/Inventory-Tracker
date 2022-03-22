package com.fazemeright.myinventorytracker.domain.authentication.adapter

import com.fazemeright.myinventorytracker.domain.models.User
import com.google.firebase.auth.FirebaseUser

class UserAdapterForFirebaseUser(val firebaseUser: FirebaseUser) : User() {
}