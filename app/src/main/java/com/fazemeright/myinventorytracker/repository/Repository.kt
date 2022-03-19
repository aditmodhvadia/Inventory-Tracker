package com.fazemeright.myinventorytracker.repository

import androidx.lifecycle.LiveData
import com.fazemeright.myinventorytracker.domain.authentication.AuthenticationResult
import com.fazemeright.myinventorytracker.domain.models.BagItem
import com.fazemeright.myinventorytracker.domain.models.InventoryItem
import com.fazemeright.myinventorytracker.domain.models.ItemWithBag
import com.fazemeright.myinventorytracker.domain.models.Result
import com.google.firebase.auth.FirebaseUser

/**
 * Specifies api interactions with repository level.
 *
 * @author Adit Modhvadia
 * @since 2.1.1
 */
interface Repository {
    /**
     * Determine if user is signed in.
     *
     * @return [Result] with <code>true</code> if successful, else <code>false</code>
     */
    fun isUserSignedIn(): Result<Boolean>

    /**
     * Register new user.
     *
     * @param email user email
     * @param password user password
     *
     * @return [Result] with registered [AuthenticationResult]
     */
    suspend fun registerWithEmailPassword(
        email: String,
        password: String
    ): kotlin.Result<AuthenticationResult>

    /**
     * Clear all [BagItem] from local.
     */
    suspend fun clearBagItems()

    /**
     * Add new [BagItem].
     *
     * @param newBag [BagItem] to be added
     */
    suspend fun addBag(newBag: BagItem)

    /**
     * Get [LiveData] of [List] of [BagItem].
     */
    fun getAllBags(): LiveData<List<BagItem>>

    /**
     * Get all [BagItem] names with [LiveData].
     */
    fun getAllBagNames(): LiveData<List<String>>

    /**
     * Clear all [InventoryItem] from local.
     */
    suspend fun clearInventoryItems()

    /**
     * Get [BagItem.bagId].
     *
     * @param bagName [BagItem.bagName]
     */
    suspend fun getBagIdWithName(bagName: String): Int

    /**
     * Insert new [InventoryItem].
     *
     * @param newItem new [InventoryItem]
     */
    suspend fun insertInventoryItem(newItem: InventoryItem)

    /**
     * Get [InventoryItem] with [LiveData].
     *
     * @param itemId [InventoryItem.itemId]
     */
    fun getItemWithBagFromId(itemId: Int): LiveData<ItemWithBag>

    /**
     * Update [InventoryItem].
     *
     * @param item [InventoryItem] to be updated
     */
    suspend fun updateInventoryItem(item: InventoryItem?)

    /**
     * Get [ItemWithBag] with [BagItem] through livedata.
     */
    fun getItemsWithBagLive(): LiveData<List<ItemWithBag>>

    /**
     * Search for inventory items with given search string
     */
    suspend fun searchInventoryItems(searchText: String): List<ItemWithBag>

    /**
     * Get inventory item with given id
     */
    suspend fun getInventoryItemById(itemId: Int): InventoryItem?

    /**
     * Delete an [InventoryItem].
     *
     * @param item [InventoryItem] to be deleted
     */
    suspend fun deleteInventoryItem(item: InventoryItem)

    /**
     * Login user.
     *
     * @param email User email
     * @param password User password
     *
     * @return [Result] with [FirebaseUser]
     */
    suspend fun performLogin(email: String, password: String): Result<FirebaseUser>

    /**
     * Sync the local database and cloud database with each other.
     */
    suspend fun syncLocalAndCloud()

    /**
     * Log out the user.
     */
    suspend fun logoutUser()

    /**
     * Sign in user.
     *
     * @param idToken Id token from Federated sign in
     *
     * @return [Result] of [FirebaseUser]
     */
    suspend fun signInWithToken(idToken: String): Result<FirebaseUser>
}
