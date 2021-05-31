package com.fazemeright.myinventorytracker.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.PagingData
import com.fazemeright.myinventorytracker.domain.models.BagItem
import com.fazemeright.myinventorytracker.domain.models.InventoryItem
import com.fazemeright.myinventorytracker.domain.models.ItemWithBag
import com.fazemeright.myinventorytracker.domain.models.Result
import com.google.firebase.auth.FirebaseUser

interface Repository {
    /**
     * Determine if user is signed in
     */
    fun isUserSignedIn(): Result<Boolean>

    /**
     * Register new user with email and password
     */
    suspend fun registerWithEmailPassword(email: String, password: String): Result<FirebaseUser>

    /**
     * Clear Bags from local
     */
    suspend fun clearBagItems()

    /**
     * Add new Bag
     */
    suspend fun addBag(newBag: BagItem)

    /**
     * Get all bags with livedata
     */
    fun getAllBags(): LiveData<List<BagItem>>

    /**
     * Get all bag names with livedata
     */
    fun getAllBagNames(): LiveData<List<String>>

    /**
     * Clear all inventory items from local
     */
    suspend fun clearInventoryItems()

    /**
     * Get bag id with the given name
     */
    suspend fun getBagIdWithName(bagName: String): Int

    /**
     * Insert inventory item
     */
    suspend fun insertInventoryItem(newItem: InventoryItem)

    /**
     * Get inventory item for the given bag id
     */
    fun getItemWithBagFromId(itemId: Int): LiveData<ItemWithBag>

    /**
     * Update the inventory item
     */
    suspend fun updateInventoryItem(item: InventoryItem?)

    /**
     * Get items with bag data through livedata
     */
    fun getItemsWithBagLive(): LiveData<PagingData<ItemWithBag>>

    /**
     * Search for inventory items with given search string
     */
    fun searchInventoryItems(searchText: String): LiveData<PagingData<ItemWithBag>>

    /**
     * Get inventory item with given id
     */
    suspend fun getInventoryItemById(itemId: Int): InventoryItem?

    /**
     * Delete the given inventory item
     */
    suspend fun deleteInventoryItem(item: InventoryItem)

    /**
     * Login user with given email and password
     */
    suspend fun performLogin(email: String, password: String): Result<FirebaseUser>

    /**
     * Sync the local database and cloud database with each other
     */
    suspend fun syncLocalAndCloud()

    /**
     * Log out the user
     */
    suspend fun logoutUser()

    suspend fun signInWithToken(idToken: String): Result<FirebaseUser>
}
