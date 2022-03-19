package com.fazemeright.myinventorytracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.fazemeright.inventorytracker.database.dao.BagItemDao
import com.fazemeright.inventorytracker.database.dao.InventoryItemDao
import com.fazemeright.myinventorytracker.domain.authentication.AuthenticationResult
import com.fazemeright.myinventorytracker.domain.authentication.UserAuthentication
import com.fazemeright.myinventorytracker.domain.authentication.firebase.FireBaseUserAuthentication
import com.fazemeright.myinventorytracker.domain.database.online.OnlineDatabaseStore
import com.fazemeright.myinventorytracker.domain.database.online.firestore.FireBaseOnlineDatabaseStore
import com.fazemeright.myinventorytracker.domain.mappers.entity.BagItemEntityMapper
import com.fazemeright.myinventorytracker.domain.mappers.entity.InventoryItemEntityMapper
import com.fazemeright.myinventorytracker.domain.mappers.entity.ItemWithBagEntityMapper
import com.fazemeright.myinventorytracker.domain.models.BagItem
import com.fazemeright.myinventorytracker.domain.models.InventoryItem
import com.fazemeright.myinventorytracker.domain.models.ItemWithBag
import com.fazemeright.myinventorytracker.domain.models.Result
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@ViewModelScoped
class InventoryRepository @Inject constructor(
    private val bagItemDao: BagItemDao,
    private val inventoryItemDao: InventoryItemDao,
//    private val apiService: SampleNetworkInterface
) : Repository {
    private val userAuthentication: UserAuthentication = FireBaseUserAuthentication
    private val onlineDatabaseStore: OnlineDatabaseStore = FireBaseOnlineDatabaseStore

    override fun isUserSignedIn(): Result<Boolean> {
        return if (userAuthentication.isUserSignedIn()) {
            Result.Success(
                msg = "User is signed in",
                data = true
            )
        } else {
            Result.Error(msg = "User is not signed in")
        }
    }

    override suspend fun registerWithEmailPassword(
        email: String,
        password: String
    ): kotlin.Result<AuthenticationResult> {
        return withContext(Dispatchers.IO) {
            userAuthentication.register(email, password)
        }
    }

    override suspend fun clearBagItems() {
        withContext(Dispatchers.IO) {
            bagItemDao.clear()
        }
    }

    override suspend fun addBag(newBag: BagItem) {
        withContext(Dispatchers.IO) {
            Timber.d(newBag.toString())
            val itemId = bagItemDao.insert(BagItemEntityMapper.mapToEntity(newBag))

            val item = bagItemDao.get(itemId)

//            item?.let { onlineDatabaseStore.storeBag(it) }
        }
    }

    override fun getAllBags() =
        bagItemDao.getAllBags().map {
            it.map { bagItemEntity ->
                BagItemEntityMapper.mapFromEntity(bagItemEntity)
            }
        }

    override fun getAllBagNames() = bagItemDao.getAllBagNames()

    override suspend fun clearInventoryItems() {
        withContext(Dispatchers.IO) {
            inventoryItemDao.clear()
        }
    }

    override suspend fun getBagIdWithName(bagName: String): Int {
        return withContext(Dispatchers.IO) {
            bagItemDao.getBagIdWithName(bagName)
        }
    }

    override suspend fun insertInventoryItem(newItem: InventoryItem) {
        withContext(Dispatchers.IO) {
            val itemId = inventoryItemDao.insert(InventoryItemEntityMapper.mapToEntity(newItem))

            val item = inventoryItemDao.getById(itemId.toInt())

//            item?.let { onlineDatabaseStore.storeInventoryItem(it) }
        }
    }

    override fun getItemWithBagFromId(itemId: Int): LiveData<ItemWithBag> =
        inventoryItemDao.getItemWithBagFromId(itemId)
            .map { ItemWithBagEntityMapper.mapFromEntity(it) }

    override suspend fun updateInventoryItem(item: InventoryItem?) {
        withContext(Dispatchers.IO) {
            item?.let { inventoryItemDao.update(InventoryItemEntityMapper.mapToEntity(it)) }
        }
    }

    override fun getItemsWithBagLive(): LiveData<List<ItemWithBag>> {
        return inventoryItemDao.getItemsWithBagLive().map {
            it.map { entity ->
                ItemWithBagEntityMapper.mapFromEntity(entity)
            }
        }
    }

    override suspend fun searchInventoryItems(searchText: String): List<ItemWithBag> {
        return withContext(Dispatchers.IO) {
            inventoryItemDao.searchItems(searchText).map {
                ItemWithBagEntityMapper.mapFromEntity(it)
            }
        }
    }

    override suspend fun getInventoryItemById(itemId: Int): InventoryItem? {
        return withContext(Dispatchers.IO) {
            inventoryItemDao.getById(itemId)?.let {
                InventoryItemEntityMapper.mapFromEntity(it)
            }
        }
    }

    override suspend fun deleteInventoryItem(item: InventoryItem) {
        withContext(Dispatchers.IO) {
            inventoryItemDao.deleteItem(InventoryItemEntityMapper.mapToEntity(item))

            onlineDatabaseStore.deleteInventoryItem(item)
        }
    }

    override suspend fun performLogin(email: String, password: String): Result<FirebaseUser> {
        return withContext(Dispatchers.IO) {
            userAuthentication.signIn(email, password)
        }
    }

    override suspend fun signInWithToken(idToken: String): Result<FirebaseUser> {
        return withContext(Dispatchers.IO) {
            try {
                val result = userAuthentication.signIn(idToken).await()
                Result.Success(data = result.user!!, msg = "User logged in successfully")
            } catch (e: Exception) {
                Timber.e(e)
                Result.Error(e, "Error occurred, user not logged in")
            }
        }
    }

    /**
     * Sync the local database and cloud database with each other
     */
    override suspend fun syncLocalAndCloud() {
//        TODO: Make these asynchronous
        Timber.d("Sync called")
        withContext(Dispatchers.IO) {
            val bagItemsInLocal = bagItemDao.getAllBagsList()
//            onlineDatabaseStore.batchWriteBags(bagItemsInLocal)

            val inventoryItemsInLocal = inventoryItemDao.getAllInventoryItemsList()
//            onlineDatabaseStore.batchWriteInventoryItems(inventoryItemsInLocal)

            when (val bagItemsInCloudResult = onlineDatabaseStore.getAllBags()) {
                is Result.Success -> {
                    bagItemDao.insertAll(bagItemsInCloudResult.data.map {
                        BagItemEntityMapper.mapToEntity(it)
                    })
                }
                is Result.Error -> {
                    Timber.e(bagItemsInCloudResult.exception)
                    throw InterruptedException("Did not fetch Bag items from the cloud")
                }
            }

            when (val inventoryItemsInCloudResult = onlineDatabaseStore.getAllInventoryItems()) {
                is Result.Success -> {
                    inventoryItemDao.insertAll(inventoryItemsInCloudResult.data.map {
                        InventoryItemEntityMapper.mapToEntity(it)
                    })
                }
                is Result.Error -> {
                    Timber.e(inventoryItemsInCloudResult.exception)
                    throw InterruptedException("Did not fetch Inventory items from the cloud")
                }
            }
        }
    }

    override suspend fun logoutUser() {
        userAuthentication.logout()
        withContext(Dispatchers.IO) {
            inventoryItemDao.clear()
            bagItemDao.clear()
        }
//        TODO: Clear Shared Preferences
    }
}
