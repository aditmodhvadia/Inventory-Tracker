package com.fazemeright.myinventorytracker.firebase.api

import com.fazemeright.myinventorytracker.database.bag.BagItem
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.firebase.api.FireBaseUserAuthentication.getCurrentUserUUID
import com.fazemeright.myinventorytracker.firebase.models.Result
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * @author Adit Modhvadia
 */
object FireBaseApiManager : FireBaseApiWrapper() {

    /**
     * Get document reference for the current logged in user
     */
    private fun getUserDocument(): DocumentReference? {
        return getCurrentUserUUID()?.let {
            usersCollection.document(it)
        }
    }

    suspend fun storeInventoryItem(item: InventoryItem): Result<Boolean> {
        return storeItem(
            item,
            inventoryItemsCollection?.document(item.itemId.toString()),
            "Inventory Item stored successfully"
        )
    }

    suspend fun storeBag(item: BagItem): Result<Boolean> {
        return storeItem(
            item,
            bagsCollection?.document(item.bagId.toString()),
            "Bag stored successfully"
        )
    }

    suspend fun deleteInventoryItem(item: InventoryItem): Result<Boolean> {
        return deleteItem(inventoryItemsCollection?.document(item.itemId.toString()))
    }

    suspend fun deleteBag(bag: BagItem): Result<Boolean> {
        return deleteItem(bagsCollection?.document(bag.bagId.toString()))
    }

    private suspend fun deleteItem(docRef: DocumentReference?): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                if (docRef != null) {
                    deleteDocument(docRef).await()
                    Timber.d("Item deleted successfully at $docRef")
                    Result.Success(data = true, msg = "Item deleted")
                } else {
                    Timber.e("Error occurred while deleting Item at $docRef")
                    Result.Error(msg = "User not signed in!")
                }
            } catch (e: Exception) {
                Timber.e("Error occurred while deleting Item at $docRef")
                Timber.e(e)
                Result.Error(exception = e, msg = "Error occurred")
            }
        }
    }

    private suspend fun <T> storeItem(
        item: T,
        docRef: DocumentReference?,
        successMsg: String
    ): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                if (docRef != null) {
                    writeData(item!!, docRef).await()
                    Timber.d("Item $item stored successfully at $docRef")
                    Result.Success(data = true, msg = successMsg)
                } else {
                    Timber.e("Error occurred while storing Item $item at $docRef")
                    Result.Error(msg = "User not signed in!")
                }
            } catch (e: Exception) {
                Timber.e("Error occurred while storing Item $item at $docRef")
                Timber.e(e)
                Result.Error(exception = e, msg = "Error occurred")
            }
        }
    }

    suspend fun getAllBags(): Result<List<BagItem>> {
        return getItems(bagsCollection)
    }

    suspend fun getAllInventoryItems(): Result<List<InventoryItem>> {
        return getItems(inventoryItemsCollection)
    }

    fun batchWriteBags(bagItems: List<BagItem>): Task<Void> {
        return if (bagsCollection != null) {
            batchWriteData(bagItems.map { bagItem ->
                bagsCollection!!.document(bagItem.bagId.toString()) to bagItem
            }.toMap())
        } else {
            Tasks.forResult(null)
        }
    }

    fun batchWriteInventoryItems(inventoryItems: List<InventoryItem>): Task<Void> {
        return if (inventoryItemsCollection != null) {
            batchWriteData(inventoryItems.map { inventoryItem ->
                inventoryItemsCollection!!.document(inventoryItem.itemId.toString()) to inventoryItem
            }.toMap())
        } else {
            Tasks.forResult(null)
        }
    }


    /*fun deleteItem(): Result<Boolean> {

    }*/

    private val usersCollection: CollectionReference
        get() = Firebase.firestore.collection(BaseUrl.USERS)

    private val inventoryItemsCollection: CollectionReference?
        get() {
            return getUserDocument()?.collection(BaseUrl.INVENTORY_ITEMS)
        }

    val bagsCollection: CollectionReference?
        get() {
            return getUserDocument()?.collection(BaseUrl.BAGS)
        }

    object BaseUrl {
        const val USERS = "users"
        const val INVENTORY_ITEMS = "inventoryItems"
        const val BAGS = "bags"
    }
}