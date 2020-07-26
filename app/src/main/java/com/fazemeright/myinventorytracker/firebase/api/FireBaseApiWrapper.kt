package com.fazemeright.myinventorytracker.firebase.api

import com.fazemeright.myinventorytracker.firebase.interfaces.FireBaseAuthUserInterface
import com.fazemeright.myinventorytracker.firebase.interfaces.FireBaseFireStoreInterface
import com.fazemeright.myinventorytracker.firebase.models.Result
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber

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

    override fun batchWriteData(documentDataMap: Map<DocumentReference, Any>): Task<Void> {
        return Firebase.firestore.runBatch { batch ->
            for ((docRef, data) in documentDataMap.entries) {
                batch.set(docRef, data)
            }
        }
    }

    override fun readDocument(doc: DocumentReference): Task<DocumentSnapshot> {
        return doc.get()
    }

    override fun readCollection(collection: CollectionReference): Task<QuerySnapshot> {
        return collection.get()
    }

    suspend inline fun <reified T : Any> getItems(collection: CollectionReference?): Result<List<T>> {
        return withContext(Dispatchers.IO) {
            try {
                if (collection != null) {
                    val documents = FireBaseApiManager.readCollection(collection).await()
                    val bagItems = documents.map { it.toObject<T>() }
                    Result.Success(data = bagItems, msg = "Bags read successfully")
                } else {
                    Result.Error(msg = "Bag collection is null, User may not be signed in")
                }
            } catch (e: Exception) {
                Timber.e(e)
                Result.Error(exception = e, msg = "Error occurred")
            }
        }
    }

    override fun deleteDocument(doc: DocumentReference): Task<Void> {
        return doc.delete()
    }
}