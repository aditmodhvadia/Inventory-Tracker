package com.fazemeright.myinventorytracker.domain.database.online.mock

import android.app.Activity
import com.fazemeright.myinventorytracker.domain.database.online.OnlineDatabaseStore
import com.fazemeright.myinventorytracker.domain.models.BagItem
import com.fazemeright.myinventorytracker.domain.models.InventoryItem
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import java.util.concurrent.Executor

class MockOnlineDatabaseStoreImpl : OnlineDatabaseStore {
    private val bags: MutableMap<String, BagItem> = mutableMapOf()
    private val inventoryItems: MutableMap<String, InventoryItem> = mutableMapOf()
    private var id = 1

    override suspend fun getAllBags(): Result<List<BagItem>> {
        return Result.success(bags.values.toList())
    }

    override suspend fun getAllInventoryItems(): Result<List<InventoryItem>> {
        return Result.success(inventoryItems.values.toList())
    }

    override suspend fun storeInventoryItem(item: InventoryItem): Result<Boolean> {
        if (inventoryItems.containsKey(item.onlineId)) {
            inventoryItems[item.onlineId!!] = item
        } else {
            inventoryItems[id.toString()] = item.apply { setOnlineDatabaseStoreId(id++.toString()) }
        }
        return Result.success(true)
    }

    override suspend fun storeBag(item: BagItem): Result<Boolean> {
        if (bags.containsKey(item.onlineId)) {
            bags[item.onlineId!!] = item
        } else {
            bags[id.toString()] = item.apply { setOnlineDatabaseStoreId(id++.toString()) }
        }
        return Result.success(true)
    }

    override suspend fun deleteInventoryItem(item: InventoryItem): Result<Boolean> {
        return if (inventoryItems.containsKey(item.onlineId)) {
            Result.success(inventoryItems.remove(item.onlineId) != null)
        } else {
            Result.failure(RuntimeException("InventoryItem not in db"))
        }
    }

    override suspend fun deleteBag(bag: BagItem): Result<Boolean> {
        return if (bags.containsKey(bag.onlineId)) {
            Result.success(bags.remove(bag.onlineId) != null)
        } else {
            Result.failure(RuntimeException("BagItem not in db"))
        }
    }

    override suspend fun batchWriteBags(bagItems: List<BagItem>): Task<Void> {
        bagItems.forEach {
            storeBag(it)
        }
        return object : Task<Void>() {
            override fun addOnFailureListener(p0: OnFailureListener): Task<Void> {
                TODO("Not yet implemented")
            }

            override fun addOnFailureListener(p0: Activity, p1: OnFailureListener): Task<Void> {
                TODO("Not yet implemented")
            }

            override fun addOnFailureListener(p0: Executor, p1: OnFailureListener): Task<Void> {
                TODO("Not yet implemented")
            }

            override fun addOnSuccessListener(p0: OnSuccessListener<in Void>): Task<Void> {
                TODO("Not yet implemented")
            }

            override fun addOnSuccessListener(
                p0: Activity,
                p1: OnSuccessListener<in Void>
            ): Task<Void> {
                TODO("Not yet implemented")
            }

            override fun addOnSuccessListener(
                p0: Executor,
                p1: OnSuccessListener<in Void>
            ): Task<Void> {
                TODO("Not yet implemented")
            }

            override fun getException(): Exception? {
                TODO("Not yet implemented")
            }

            override fun getResult(): Void {
                TODO("Not yet implemented")
            }

            override fun <X : Throwable?> getResult(p0: Class<X>): Void {
                TODO("Not yet implemented")
            }

            override fun isCanceled(): Boolean {
                TODO("Not yet implemented")
            }

            override fun isComplete(): Boolean {
                TODO("Not yet implemented")
            }

            override fun isSuccessful(): Boolean {
                TODO("Not yet implemented")
            }
        }
    }

    override suspend fun batchWriteInventoryItems(inventoryItems: List<InventoryItem>): Task<Void> {
        inventoryItems.forEach {
            storeInventoryItem(it)
        }
        return object : Task<Void>() {
            override fun addOnFailureListener(p0: OnFailureListener): Task<Void> {
                TODO("Not yet implemented")
            }

            override fun addOnFailureListener(p0: Activity, p1: OnFailureListener): Task<Void> {
                TODO("Not yet implemented")
            }

            override fun addOnFailureListener(p0: Executor, p1: OnFailureListener): Task<Void> {
                TODO("Not yet implemented")
            }

            override fun addOnSuccessListener(p0: OnSuccessListener<in Void>): Task<Void> {
                TODO("Not yet implemented")
            }

            override fun addOnSuccessListener(
                p0: Activity,
                p1: OnSuccessListener<in Void>
            ): Task<Void> {
                TODO("Not yet implemented")
            }

            override fun addOnSuccessListener(
                p0: Executor,
                p1: OnSuccessListener<in Void>
            ): Task<Void> {
                TODO("Not yet implemented")
            }

            override fun getException(): Exception? {
                TODO("Not yet implemented")
            }

            override fun getResult(): Void {
                TODO("Not yet implemented")
            }

            override fun <X : Throwable?> getResult(p0: Class<X>): Void {
                TODO("Not yet implemented")
            }

            override fun isCanceled(): Boolean {
                TODO("Not yet implemented")
            }

            override fun isComplete(): Boolean {
                TODO("Not yet implemented")
            }

            override fun isSuccessful(): Boolean {
                TODO("Not yet implemented")
            }
        }
    }
}