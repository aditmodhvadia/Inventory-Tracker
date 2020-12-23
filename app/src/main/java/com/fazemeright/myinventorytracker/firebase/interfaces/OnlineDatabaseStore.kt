package com.fazemeright.myinventorytracker.firebase.interfaces

import com.fazemeright.myinventorytracker.database.bag.BagItem
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.firebase.models.Result
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