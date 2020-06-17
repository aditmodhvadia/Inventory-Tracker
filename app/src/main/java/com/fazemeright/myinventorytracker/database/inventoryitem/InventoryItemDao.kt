/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fazemeright.myinventorytracker.database.inventoryitem

import androidx.lifecycle.LiveData
import androidx.room.*
import java.io.Serializable

/**
 * Defines methods for using the InventoryItem class with Room.
 */
@Dao
interface InventoryItemDao {

    @Insert
    fun insert(item: InventoryItem)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param item new value to write
     */
    @Update
    fun update(item: InventoryItem)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from my_inventory_table WHERE itemId = :key")
    fun get(key: Long): InventoryItem?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM my_inventory_table")
    fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by item name in ascending order.
     */
    @Query("SELECT * FROM my_inventory_table ORDER BY itemName")
    fun getAllItems(): LiveData<List<InventoryItem>>

    //    @Query("SELECT * FROM my_inventory_table WHERE itemName LIKE :searchText ORDER BY itemName")
    @Query("SELECT * FROM my_inventory_table INNER JOIN my_bag_table WHERE itemName LIKE :searchText OR itemDesc LIKE :searchText ORDER BY itemName")
    fun getSearchItems(searchText: String): List<ItemInBag>

    /*@Query("SELECT * FROM my_inventory_table INNER JOIN my_bag_table ON bagId = bagId")
    fun getAllItemsAndBags()*/

    /**
     * Selects and returns the night with given nightId.
     */
    @Query("SELECT * from my_inventory_table WHERE itemId = :key")
    fun getNightWithId(key: Long): LiveData<InventoryItem>

    @Query("SELECT  * from my_bag_table INNER JOIN my_inventory_table")
    fun getAllItemsWithBag(): LiveData<List<ItemInBag>>

    data class ItemInBag(
        var itemId: Long,

        var itemName: String,

        var itemDesc: String,

        var itemQuantity: Int,

        var bagId: Long,

        var bagName: String,

        var bagColor: Int,

        var bagDesc: String
    ) : Serializable

    @Delete
    fun deleteItem(item: InventoryItem)

    @Query("DELETE FROM my_inventory_table WHERE itemId =:itemId")
    fun delete(itemId: Long)

    @Query("SELECT * FROM my_inventory_table INNER JOIN my_bag_table WHERE itemId = :itemId")
    fun getItemInBagFromId(itemId: Long): ItemInBag
}

