package com.fazemeright.myinventorytracker.database.bag

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BagItemDao {

    @Insert
    fun insert(item: BagItem)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param item new value to write
     */
    @Update
    fun update(item: BagItem)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from my_bag_table WHERE bagId = :key")
    fun get(key: Long): BagItem?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM my_bag_table")
    fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by item name in ascending order.
     */
    @Query("SELECT * FROM my_bag_table ORDER BY bagName")
    fun getAllBags(): LiveData<List<BagItem>>

    @Query("SELECT bagName FROM my_bag_table ORDER BY bagName")
    fun getAllBagNames(): LiveData<List<String>>

    @Query("SELECT bagId FROM my_bag_table WHERE bagName =:bagName LIMIT 1")
    fun getBagIdWithName(bagName: String): Long

/*
    //    @Query("SELECT * FROM my_inventory_table WHERE itemName LIKE :searchText ORDER BY itemName")
    @Query("SELECT * FROM my_inventory_table WHERE itemName LIKE :searchText OR itemDesc LIKE :searchText ORDER BY itemName")
    fun getSearchItems(searchText: String): List<InventoryItem>
*/


    /**
     * Selects and returns the night with given nightId.
     */
    @Query("SELECT * from my_bag_table WHERE bagId = :key")
    fun getBagWithId(key: Long): LiveData<BagItem>

    @Delete
    fun deleteItem(item: BagItem)

}