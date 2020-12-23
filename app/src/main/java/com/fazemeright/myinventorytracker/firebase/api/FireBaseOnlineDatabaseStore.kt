package com.fazemeright.myinventorytracker.firebase.api

import com.fazemeright.myinventorytracker.database.bag.BagItem
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.firebase.interfaces.OnlineDatabaseStore
import com.fazemeright.myinventorytracker.firebase.models.Result
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
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

object FireBaseOnlineDatabaseStore : OnlineDatabaseStore {

    /**
     * Get all items for the specified collection
     */
    private suspend inline fun <reified T : Any> getItems(collection: CollectionReference?): Result<List<T>> {
        require(collection != null) {
            "Bag collection is null, User may not be signed in"
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
            userInventoryItemsCollection?.document(item.itemId.toString()),
            "Inventory Item stored successfully"
        )
    }

    override suspend fun storeBag(item: BagItem): Result<Boolean> {
        return storeItem(
            item,
            userBagItemsCollection?.document(item.bagId.toString()),
            "Bag stored successfully"
        )
    }

    override suspend fun deleteInventoryItem(item: InventoryItem): Result<Boolean> {
        return deleteItem(userInventoryItemsCollection?.document(item.itemId.toString()))
    }

    override suspend fun deleteBag(bag: BagItem): Result<Boolean> {
        return deleteItem(userBagItemsCollection?.document(bag.bagId.toString()))
    }

    /**
     * Delete data in the given document
     */
    private suspend fun deleteItem(docRef: DocumentReference?): Result<Boolean> {
        require(docRef != null) {
            "User not logged in"
        }
        return withContext(Dispatchers.IO) {
            try {
                deleteDocument(docRef).await()
                Timber.d("Item deleted successfully at $docRef")
                Result.Success(data = true, msg = "Item deleted")
            } catch (e: Exception) {
                Timber.e(e)
                Result.Error(exception = e, msg = "Error occurred")
            }
        }
    }


    override fun batchWriteBags(bagItems: List<BagItem>): Task<Void> {
        return if (userBagItemsCollection != null) {
            batchWriteData(bagItems.map { bagItem ->
                userBagItemsCollection!!.document(bagItem.bagId.toString()) to bagItem
            }.toMap())
        } else {
            Tasks.forResult(null)
        }
    }

    override fun batchWriteInventoryItems(inventoryItems: List<InventoryItem>): Task<Void> {
        return if (userInventoryItemsCollection != null) {
            batchWriteData(inventoryItems.map { inventoryItem ->
                userInventoryItemsCollection!!.document(inventoryItem.itemId.toString()) to inventoryItem
            }.toMap())
        } else {
            Tasks.forResult(null)
        }
    }

    /**
     * Store the given item in the given document
     */
    private suspend fun <T> storeItem(
        item: T,
        docRef: DocumentReference?,
        successMsg: String
    ): Result<Boolean> {
        require(docRef != null) {
            "User not logged in"
        }
        return withContext(Dispatchers.IO) {
            try {
                writeData(item!!, docRef).await()
                Timber.d("Item $item stored successfully at $docRef")
                Result.Success(data = true, msg = successMsg)
            } catch (e: Exception) {
                Timber.e(e)
                Result.Error(exception = e, msg = "Error occurred")
            }
        }
    }

    /**
     * Write the given data in given document
     */
    private fun writeData(data: Any, doc: DocumentReference): Task<Void> {
        return doc.set(data)
    }

    /**
     * Batch write the given data in given documents
     */
    private fun batchWriteData(documentDataMap: Map<DocumentReference, Any>): Task<Void> {
        return Firebase.firestore.runBatch { batch ->
            for ((docRef, data) in documentDataMap.entries) {
                batch.set(docRef, data)
            }
        }
    }

    /**
     * Read the data from the given document
     */
    private fun readDocument(doc: DocumentReference): Task<DocumentSnapshot> {
        return doc.get()
    }

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
