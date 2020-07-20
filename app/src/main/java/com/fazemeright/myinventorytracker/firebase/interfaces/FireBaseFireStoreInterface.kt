package com.fazemeright.myinventorytracker.firebase.interfaces

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference

interface FireBaseFireStoreInterface {
    fun writeData(data: Any, doc: DocumentReference): Task<Void>

    fun deleteDocument(doc: DocumentReference): Task<Void>

//    fun getData(data: Any, doc: DocumentReference): Task<Void>
}