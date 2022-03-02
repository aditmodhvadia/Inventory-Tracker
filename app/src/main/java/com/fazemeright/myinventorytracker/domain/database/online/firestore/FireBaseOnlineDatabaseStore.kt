package com.fazemeright.myinventorytracker.domain.database.online.firestore

import com.fazemeright.myinventorytracker.domain.authentication.firebase.FireBaseUserAuthentication
import com.fazemeright.myinventorytracker.domain.database.online.OnlineDatabaseStore
import com.fazemeright.myinventorytracker.domain.models.BagItem
import com.fazemeright.myinventorytracker.domain.models.InventoryItem
import com.fazemeright.myinventorytracker.domain.models.OnlineDatabaseStoreObject
import com.fazemeright.myinventorytracker.domain.models.Result
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber

object FireBaseOnlineDatabaseStore : OnlineDatabaseStore {

    /**
     * Get all items for the specified collection
     */
    private suspend inline fun <reified T : OnlineDatabaseStoreObject> getItems(collection: CollectionReference?): Result<List<T>> {
        require(collection != null) {
            "Collection is null, User may not be signed in"
        }
        return withContext(Dispatchers.IO) {
            try {
                val documents = readCollection(collection).await()
                val bagItems = documents.map { it.toObject<T>() }
                Result.Success(data = bagItems, msg = "Bags read successfully")
            } catch (e: Exception) {
                Timber.e(e)
                Result.Error(exception = e, msg = "Error occurred")
            }
        }
    }

    override suspend fun getAllBags(): Result<List<BagItem>> {
        return getItems(userBagItemsCollection)
    }

    override suspend fun getAllInventoryItems(): Result<List<InventoryItem>> {
        return getItems(userInventoryItemsCollection)
    }

    override suspend fun storeInventoryItem(item: InventoryItem): Result<Boolean> {
        return storeItem(
            item,
            userInventoryItemsCollection,
            "Inventory Item stored successfully"
        )
    }

    override suspend fun storeBag(item: BagItem): Result<Boolean> {
        return storeItem(
            item,
            userBagItemsCollection,
            "Bag stored successfully"
        )
    }

    override suspend fun deleteInventoryItem(item: InventoryItem): Result<Boolean> {
        return deleteItem(item, userInventoryItemsCollection)
    }

    override suspend fun deleteBag(bag: BagItem): Result<Boolean> {
        return deleteItem(bag, userBagItemsCollection)
    }

    /**
     * Delete data in the given collection
     */
    private suspend fun deleteItem(
        data: OnlineDatabaseStoreObject,
        collection: CollectionReference?
    ): Result<Boolean> {
        require(collection != null) {
            "User not logged in"
        }
        require(data.getOnlineDatabaseStoreId() != null) {
            "Online ID of data is null, cannot proceed"
        }
        return withContext(Dispatchers.IO) {
            try {
                deleteDocument(collection.document(data.getOnlineDatabaseStoreId()!!)).await()
                Result.Success(data = true, msg = "Item deleted")
            } catch (e: Exception) {
                Timber.e(e)
                Result.Error(exception = e, msg = "Error occurred")
            }
        }
    }

    override suspend fun batchWriteBags(bagItems: List<BagItem>): Task<Void> {
        return withContext(Dispatchers.IO) {
            batchWriteData(userBagItemsCollection, bagItems)
        }
    }

    override suspend fun batchWriteInventoryItems(inventoryItems: List<InventoryItem>): Task<Void> {
        return withContext(Dispatchers.IO) {
            batchWriteData(userInventoryItemsCollection, inventoryItems)
        }
    }

    /**
     * Store the given item in the given document
     */
    private suspend fun <T : OnlineDatabaseStoreObject> storeItem(
        item: T,
        collectionRef: CollectionReference?,
        successMsg: String
    ): Result<Boolean> {
        require(collectionRef != null) {
            "User not logged in"
        }
        return withContext(Dispatchers.IO) {
            try {
                writeData(item, collectionRef).await()
                Result.Success(data = true, msg = successMsg)
            } catch (e: Exception) {
                Timber.e(e)
                Result.Error(exception = e, msg = "Error occurred")
            }
        }
    }

    /*/**
     * Write the given data in given document
     */
    private fun writeData(data: Any, doc: DocumentReference): Task<Void> {
        return doc.set(data)
    }*/

    /**
     * Write the given data in given collection
     */
    private fun writeData(
        data: OnlineDatabaseStoreObject,
        collection: CollectionReference
    ): Task<Void> {
        if (data.getOnlineDatabaseStoreId() == null) {
            val onlineId = collection.document().id
            data.setOnlineDatabaseStoreId(onlineId)
        }

        return collection.document(data.getOnlineDatabaseStoreId()!!).set(data)
    }

    /**
     * Batch write the given data in given documents
     */
    private fun batchWriteData(
        collection: CollectionReference?,
        dataList: List<OnlineDatabaseStoreObject>
    ): Task<Void> {
        return if (collection != null) {
            Firebase.firestore.runBatch { batch ->
                dataList.forEach { item ->
                    item.getOnlineDatabaseStoreId()?.let {
                        batch.set(collection.document(it), item)
                    }
                }
            }
        } else {
            Tasks.forResult(null)
        }
    }

    /*/**
     * Read the data from the given document
     */
    private fun readDocument(doc: DocumentReference): Task<DocumentSnapshot> {
        return doc.get()
    }*/

    /**
     * Read the data from the given collection
     */
    private fun readCollection(collection: CollectionReference): Task<QuerySnapshot> {
        return collection.get()
    }

    /**
     * Delete the data from the given document
     */
    private fun deleteDocument(doc: DocumentReference): Task<Void> {
        return doc.delete()
    }

    /**
     * Collection pointing to all the users data
     */
    private val usersCollection: CollectionReference
        get() = Firebase.firestore.collection(BaseUrl.USERS)

    private val userInventoryItemsCollection: CollectionReference?
        get() {
            return getUserDocument()?.collection(BaseUrl.INVENTORY_ITEMS)
        }

    private val userBagItemsCollection: CollectionReference?
        get() {
            return getUserDocument()?.collection(BaseUrl.BAGS)
        }

    /**
     * Get document reference for the current logged in user
     */
    private fun getUserDocument(): DocumentReference? {
        return FireBaseUserAuthentication.getCurrentUserUUID()?.let {
            usersCollection.document(it)
        }
    }

    object BaseUrl {
        const val USERS = "users"
        const val INVENTORY_ITEMS = "inventoryItems"
        const val BAGS = "bags"
    }
}
