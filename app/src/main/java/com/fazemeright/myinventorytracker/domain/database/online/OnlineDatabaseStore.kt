package com.fazemeright.myinventorytracker.domain.database.online

import com.fazemeright.myinventorytracker.domain.models.BagItem
import com.fazemeright.myinventorytracker.domain.models.InventoryItem
import com.fazemeright.myinventorytracker.domain.models.Result
import com.google.android.gms.tasks.Task

interface OnlineDatabaseStore {
    suspend fun getAllBags(): Result<List<BagItem>>

    suspend fun getAllInventoryItems(): Result<List<InventoryItem>>

    suspend fun storeInventoryItem(item: InventoryItem): Result<Boolean>

    suspend fun storeBag(item: BagItem): Result<Boolean>

    suspend fun deleteInventoryItem(item: InventoryItem): Result<Boolean>

    suspend fun deleteBag(bag: BagItem): Result<Boolean>

    fun batchWriteBags(bagItems: List<BagItem>): Task<Void>

    fun batchWriteInventoryItems(inventoryItems: List<InventoryItem>): Task<Void>
}
