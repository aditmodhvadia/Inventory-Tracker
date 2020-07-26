package com.fazemeright.myinventorytracker.firebase.interfaces

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface FireBaseFireStoreInterface {
    fun writeData(data: Any, doc: DocumentReference): Task<Void>

    fun batchWriteData(
        documentDataMap: Map<DocumentReference, Any>
    ): Task<Void>

    fun deleteDocument(doc: DocumentReference): Task<Void>

    fun readDocument(doc: DocumentReference): Task<DocumentSnapshot>

    fun readCollection(collection: CollectionReference): Task<QuerySnapshot>

//    fun getData(data: Any, doc: DocumentReference): Task<Void>
}