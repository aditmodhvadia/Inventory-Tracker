package com.fazemeright.inventorytracker.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.fazemeright.inventorytracker.database.models.InventoryItemEntity
import com.fazemeright.inventorytracker.database.models.ItemWithBagEntity

/**
 * Defines methods for using the InventoryItem class with Room.
 */
@Dao
interface InventoryItemDao : BaseDao<InventoryItemEntity> {

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param id startTimeMilli to match
     */
    @Query("SELECT * from my_inventory_table WHERE itemId = :id")
    fun getById(id: Int): InventoryItemEntity?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM my_inventory_table")
    fun clear()

    @Transaction
    @Query("SELECT * FROM my_inventory_table WHERE itemId = :itemId")
    fun getItemWithBagFromId(itemId: Int): LiveData<ItemWithBagEntity>

    @Query("SELECT * FROM my_inventory_table WHERE itemName =:name")
    fun findItemsByName(name: String): List<InventoryItemEntity>

    @Query("SELECT * FROM my_inventory_table ORDER BY itemName")
    fun getAllInventoryItemsList(): List<InventoryItemEntity>

    @Transaction
    @Query("SELECT * FROM my_inventory_table")
    fun getItemsWithBagLive(): LiveData<List<InventoryItemEntity>>

    @Transaction
    @Query("SELECT * FROM my_inventory_table")
    fun getItemsWithBag(): List<InventoryItemEntity>

    @Transaction
    @Query("SELECT * FROM my_inventory_table WHERE itemName LIKE '%' || :searchString || '%' ORDER BY itemName")
    fun searchItems(searchString: String): List<InventoryItemEntity>
}
