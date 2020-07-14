package com.fazemeright.myinventorytracker.data

import com.fazemeright.myinventorytracker.database.bag.BagItem
import com.fazemeright.myinventorytracker.database.bag.BagItemDao
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItemDao
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

    suspend fun signInWithEmailPassword(email: String, password: String): Result<FirebaseUser> {
        return withContext(Dispatchers.IO) {
            try {
                val result = FireBaseApiManager.signInWithEmailPassword(email, password).await()
                if (result.user != null) Result.Success(data = result.user!!) else Result.Error(msg = "Some error occurred")
            } catch (e: Exception) {
                Timber.e(e)
                Result.Error(msg = "")
            }
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
            bagItemDao.insert(newBag)
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

    suspend fun insertNewInventoryItem(newItem: InventoryItem) {
        withContext(Dispatchers.IO) {
            inventoryItemDao.insert(newItem)
        }
    }
}