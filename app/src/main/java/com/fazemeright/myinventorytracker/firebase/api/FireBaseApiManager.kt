package com.fazemeright.myinventorytracker.firebase.api

import com.fazemeright.myinventorytracker.utils.Validator.isEmailValid
import com.fazemeright.myinventorytracker.utils.Validator.isPasswordValid
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Adit Modhvadia
 */
object FireBaseApiManager : FireBaseApiWrapper() {

    suspend fun signInWithEmailPassword(email: String, password: String): Task<AuthResult> {
        require(email.isEmailValid()) {
            "Email address is invalid"
        }

        require(email.isPasswordValid()) {
            "Password is invalid"
        }
        return withContext(Dispatchers.IO) {
            signIn(email, password)
        }
    }

    suspend fun registerWithEmailPassword(email: String, password: String): Task<AuthResult> {
        require(email.isEmailValid()) {
            "Email address is invalid"
        }

        require(email.isPasswordValid()) {
            "Password is invalid"
        }
        return withContext(Dispatchers.IO) {
            register(email, password)
        }
    }

    fun isUserSignedIn(): Boolean = userSignedIn()

    /**
     * Get document reference for the current logged in user
     */
    private fun getUserDocument(): DocumentReference? {
        return getCurrentUserUUID()?.let {
            usersCollection.document(it)
        }
    }

    private val usersCollection: CollectionReference
        get() = Firebase.firestore.collection(BaseUrl.USERS)

    private val inventoryItemsCollection: CollectionReference?
        get() {
            return getUserDocument()?.collection(BaseUrl.INVENTORY_ITEMS)
        }

    private val bagsCollection: CollectionReference?
        get() {
            return getUserDocument()?.collection(BaseUrl.BAGS)
        }

    object BaseUrl {
        const val USERS = "users"
        const val INVENTORY_ITEMS = "inventoryItems"
        const val BAGS = "bags"
    }
}