package com.fazemeright.myinventorytracker.repository

import androidx.lifecycle.LiveData
import com.fazemeright.myinventorytracker.database.bag.BagItem
import com.fazemeright.myinventorytracker.database.bag.BagItemDao
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItemDao
import com.fazemeright.myinventorytracker.database.inventoryitem.ItemWithBag
import com.fazemeright.myinventorytracker.firebase.api.FireBaseApiManager
import com.fazemeright.myinventorytracker.firebase.models.Result
import com.fazemeright.myinventorytracker.network.interfaces.SampleNetworkInterface
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val bagItemDao: BagItemDao,
    private val inventoryItemDao: InventoryItemDao,
    private val apiService: SampleNetworkInterface
) {

    fun isUserSignedIn(): Result<Boolean> {
        return if (FireBaseApiManager.isUserSignedIn()) {
            Result.Success(
                msg = "User is singed in",
                data = true
            )
        } else {
            Result.Error(msg = "User is not signed in")
        }
    }

    suspend fun registerWithEmailPassword(email: String, password: String): Result<FirebaseUser> {
        return withContext(Dispatchers.IO) {
            try {
                val result = FireBaseApiManager.registerWithEmailPassword(email, password).await()
                if (result.user != null)
                    Result.Success(data = result.user!!)
                else
                    Result.Error(msg = "Some error occurred")
            } catch (e: Exception) {
                Timber.e(e)
                Result.Error(msg = "")
            }
        }
    }

    suspend fun clearBagItems() {
        withContext(Dispatchers.IO) {
            bagItemDao.clear()
        }
    }

    suspend fun addBag(newBag: BagItem) {
        withContext(Dispatchers.IO) {
            Timber.d(newBag.toString())
            val itemId = bagItemDao.insert(newBag)

            val item = bagItemDao.get(itemId)

            item?.let { FireBaseApiManager.storeBag(it) }
        }
    }

    fun getAllBags() = bagItemDao.getAllBags()

    fun getAllBagNames() = bagItemDao.getAllBagNames()

    suspend fun clearInventoryItems() {
        withContext(Dispatchers.IO) {
            inventoryItemDao.clear()
        }
    }

    suspend fun getBagIdWithName(bagName: String): Int {
        return withContext(Dispatchers.IO) {
            bagItemDao.getBagIdWithName(bagName)
        }
    }

    suspend fun insertInventoryItem(newItem: InventoryItem) {
        withContext(Dispatchers.IO) {
            val itemId = inventoryItemDao.insert(newItem)

            val item = inventoryItemDao.getById(itemId.toInt())

            item?.let { FireBaseApiManager.storeInventoryItem(it) }
        }
    }

    fun getItemWithBagFromId(itemId: Int): LiveData<ItemWithBag> =
        inventoryItemDao.getItemWithBagFromId(itemId)

    suspend fun updateItem(item: InventoryItem?) {
        withContext(Dispatchers.IO) {
            item?.let { inventoryItemDao.update(it) }
        }
    }

    fun getItemsWithBagLive(): LiveData<List<ItemWithBag>> {
        return inventoryItemDao.getItemsWithBagLive()
    }

    suspend fun searchInventoryItems(searchText: String): List<ItemWithBag> {
        return withContext(Dispatchers.IO) {
            inventoryItemDao.searchItems(searchText)
        }

    }

    suspend fun getInventoryItemById(itemId: Int): InventoryItem? {
        return withContext(Dispatchers.IO) {
            inventoryItemDao.getById(itemId)
        }
    }

    suspend fun deleteInventoryItem(item: InventoryItem) {
        withContext(Dispatchers.IO) {
            inventoryItemDao.deleteItem(item)

            FireBaseApiManager.deleteInventoryItem(item)
        }
    }

    suspend fun performLogin(email: String, password: String): Result<FirebaseUser> {
        return withContext(Dispatchers.IO) {
            try {
                val result = FireBaseApiManager.signInWithEmailPassword(email, password).await()
                Result.Success(data = result.user!!, msg = "User Logged in Successfully")
            } catch (e: Exception) {
                Timber.e(e)
                Result.Error(e, "Error occurred, user not logged in")
            }
        }
    }

    /**
     * Sync the local database and cloud database with each other
     */
    suspend fun syncLocalAndCloud() {
//        TODO: Make these asynchronous
        Timber.d("Sync called")
        withContext(Dispatchers.IO) {
            val bagItemsInLocal = bagItemDao.getAllBagsList()
            FireBaseApiManager.batchWriteBags(bagItemsInLocal)

            val inventoryItemsInLocal = inventoryItemDao.getAllInventoryItemsList()
            FireBaseApiManager.batchWriteInventoryItems(inventoryItemsInLocal)

            when (val bagItemsInCloudResult = FireBaseApiManager.getAllBags()) {
                is Result.Success -> {
                    bagItemDao.insertAll(bagItemsInCloudResult.data)
                }
                is Result.Error -> {
                    Timber.e(bagItemsInCloudResult.exception)
                    throw InterruptedException("Did not fetch Bag items from the cloud")
                }

            }

            when (val inventoryItemsInCloudResult = FireBaseApiManager.getAllInventoryItems()) {
                is Result.Success -> {
                    inventoryItemDao.insertAll(inventoryItemsInCloudResult.data)
                }
                is Result.Error -> {
                    Timber.e(inventoryItemsInCloudResult.exception)
                    throw InterruptedException("Did not fetch Inventory items from the cloud")
                }
            }
        }
    }

    suspend fun logoutUser() {
        FireBaseApiManager.logout()
        withContext(Dispatchers.IO) {
            inventoryItemDao.clear()
            bagItemDao.clear()
        }
//        TODO: Clear Shared Preferences
    }
}







