package com.fazemeright.myinventorytracker.database.inventoryitem

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.fazemeright.myinventorytracker.database.base.BaseDao

/**
 * Defines methods for using the InventoryItem class with Room.
 */
@Dao
interface InventoryItemDao : BaseDao<InventoryItem> {

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param id startTimeMilli to match
     */
    @Query("SELECT * from my_inventory_table WHERE itemId = :id")
    fun getById(id: Int): InventoryItem?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM my_inventory_table")
    fun clear()

    @Query("SELECT * FROM my_inventory_table WHERE itemId = :itemId")
    fun getItemWithBagFromId(itemId: Int): LiveData<ItemWithBag>

    @Query("SELECT * FROM my_inventory_table WHERE itemName =:name")
    fun findItemsByName(name: String): List<InventoryItem>

    @Transaction
    @Query("SELECT * FROM my_inventory_table")
    fun getItemsWithBagLive(): LiveData<List<ItemWithBag>>

    @Transaction
    @Query("SELECT * FROM my_inventory_table")
    fun getItemsWithBag(): List<ItemWithBag>

    @Transaction
    @Query("SELECT * FROM my_inventory_table WHERE itemName LIKE '%' || :searchString || '%' ORDER BY itemName")
    fun searchItems(searchString: String): List<ItemWithBag>
}
