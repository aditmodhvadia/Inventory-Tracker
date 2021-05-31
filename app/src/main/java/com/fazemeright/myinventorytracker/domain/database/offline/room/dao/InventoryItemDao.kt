package com.fazemeright.myinventorytracker.domain.database.offline.room.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.fazemeright.myinventorytracker.domain.models.InventoryItem
import com.fazemeright.myinventorytracker.domain.models.ItemWithBag

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

    @Transaction
    @Query("SELECT * FROM my_inventory_table WHERE itemId = :itemId")
    fun getItemWithBagFromId(itemId: Int): LiveData<ItemWithBag>

    @Query("SELECT * FROM my_inventory_table WHERE itemName =:name")
    fun findItemsByName(name: String): List<InventoryItem>

    @Query("SELECT * FROM my_inventory_table ORDER BY itemName")
    fun getAllInventoryItemsList(): List<InventoryItem>

    @Transaction
    @Query("SELECT * FROM my_inventory_table")
    fun getItemsWithBagLive(): PagingSource<Int, ItemWithBag>/*LiveData<List<ItemWithBag>>*/

    @Transaction
    @Query("SELECT * FROM my_inventory_table")
    fun getItemsWithBag(): List<ItemWithBag>

    @Transaction
    @Query("SELECT * FROM my_inventory_table WHERE itemName LIKE '%' || :searchString || '%' ORDER BY itemName")
    fun searchItems(searchString: String): PagingSource<Int, ItemWithBag>
}
