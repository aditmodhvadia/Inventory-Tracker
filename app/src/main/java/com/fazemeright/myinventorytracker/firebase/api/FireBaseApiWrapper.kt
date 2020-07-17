package com.fazemeright.myinventorytracker.firebase.api

import com.fazemeright.myinventorytracker.firebase.interfaces.FireBaseAuthUserInterface
import com.fazemeright.myinventorytracker.firebase.interfaces.FireBaseFireStoreInterface
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase

abstract class FireBaseApiWrapper : FireBaseAuthUserInterface, FireBaseFireStoreInterface {

    override fun signIn(email: String, password: String): Task<AuthResult> =
        Firebase.auth.signInWithEmailAndPassword(email, password)

    override fun signIn(idToken: String): Task<AuthResult> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return Firebase.auth.signInWithCredential(credential)
    }

    override fun register(email: String, password: String): Task<AuthResult> =
        Firebase.auth.createUserWithEmailAndPassword(email, password)

    override fun logout() = Firebase.auth.signOut()


    override fun currentUser(): FirebaseUser? = Firebase.auth.currentUser

    override fun getCurrentUserUUID(): String? = FireBaseApiManager.currentUser()?.uid

    override fun userSignedIn(): Boolean = currentUser() != null

    override fun sendPasswordResetEmail(): Task<Void>? {
        return currentUser()?.sendEmailVerification()
    }

    override fun isUserVerified(): Boolean = currentUser()?.isEmailVerified ?: false

    override fun writeData(data: Any, doc: DocumentReference): Task<Void> {
        return doc.set(data)
    }
}