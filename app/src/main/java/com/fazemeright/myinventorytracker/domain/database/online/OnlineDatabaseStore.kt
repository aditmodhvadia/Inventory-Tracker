package com.fazemeright.myinventorytracker.domain.database.online

import com.fazemeright.myinventorytracker.domain.models.BagItem
import com.fazemeright.myinventorytracker.domain.models.InventoryItem
import com.fazemeright.myinventorytracker.domain.models.Result
import com.google.android.gms.tasks.Task

/**
 * @author Adit Modhvadia
 * @since 2.1.1
 */
interface OnlineDatabaseStore {

    /**
     * Get all [BagItem]
     *
     * @return [Result] of a [List] of all [BagItem]
     */
    suspend fun getAllBags(): Result<List<BagItem>>

    /**
     * Get all [InventoryItem]
     *
     * @return [Result] of a [List] of all [InventoryItem]
     */
    suspend fun getAllInventoryItems(): Result<List<InventoryItem>>

    /**
     * Save an [InventoryItem]
     *
     * @return [Result] of operation, <code>true</code> if successful, else <code>false</code>
     */
    suspend fun storeInventoryItem(item: InventoryItem): Result<Boolean>

    /**
     * Save an [BagItem]
     *
     * @return [Result] of operation, <code>true</code> if successful, else <code>false</code>
     */
    suspend fun storeBag(item: BagItem): Result<Boolean>

    /**
     * Delete an [InventoryItem]
     *
     * @return [Result] of operation, <code>true</code> if successful, else <code>false</code>
     */
    suspend fun deleteInventoryItem(item: InventoryItem): Result<Boolean>

    /**
     * Save an [BagItem]
     *
     * @return [Result] of operation, <code>true</code> if successful, else <code>false</code>
     */
    suspend fun deleteBag(bag: BagItem): Result<Boolean>

    /**
     * Batch write [List] of [BagItem]
     *
     * @return Empty [Result] for operation
     */
    fun batchWriteBags(bagItems: List<BagItem>): Task<Void>

    /**
     * Batch write [List] of [InventoryItem]
     *
     * @return Empty [Result] for operation
     */
    fun batchWriteInventoryItems(inventoryItems: List<InventoryItem>): Task<Void>
}
