package com.fazemeright.myinventorytracker.database.inventoryitem

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fazemeright.myinventorytracker.database.bag.BagItem
import com.fazemeright.myinventorytracker.database.base.BaseDao
import java.io.Serializable

/**
 * Defines methods for using the InventoryItem class with Room.
 */
@Dao
interface InventoryItemDao : BaseDao<InventoryItem> {

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from my_inventory_table WHERE itemId = :key")
    fun get(key: Int): InventoryItem?

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

    @Query("DELETE FROM my_inventory_table WHERE itemId =:itemId")
    fun delete(itemId: Long)

    @Query("SELECT * FROM my_inventory_table WHERE itemId = :itemId")
    fun getItemWithBagFromId(itemId: Int): ItemWithBag

    @Query("SELECT * FROM my_inventory_table WHERE itemName =:name")
    fun findItemsByName(name: String): List<InventoryItem>

    @Transaction
    @Query("SELECT * FROM my_inventory_table")
    fun getItemsWithBagLive(): LiveData<List<ItemWithBag>>

    @Transaction
    @Query("SELECT * FROM my_inventory_table")
    fun getItemsWithBag(): List<ItemWithBag>

    @Entity
    data class ItemWithBag(
        @Embedded val item: InventoryItem,
        @Relation(
            parentColumn = "bagOwnerId",
            entityColumn = "bagId"
        )
        val bag: BagItem
    ) : Serializable
}
