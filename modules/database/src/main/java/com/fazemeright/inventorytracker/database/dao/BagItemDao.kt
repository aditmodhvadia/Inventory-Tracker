package com.fazemeright.inventorytracker.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fazemeright.inventorytracker.database.models.BagItemEntity
import com.fazemeright.inventorytracker.database.models.InventoryItemEntity

@Dao
interface BagItemDao : BaseDao<BagItemEntity> {
    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from my_bag_table WHERE bagId = :key")
    fun get(key: Long): BagItemEntity?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM my_bag_table")
    fun clear()

    @Query("SELECT * FROM my_bag_table ORDER BY bagName")
    fun getAllBagsList(): List<BagItemEntity>

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by item name in ascending order.
     */
    @Query("SELECT * FROM my_bag_table ORDER BY bagName")
    fun getAllBags(): LiveData<List<BagItemEntity>>

    @Query("SELECT bagName FROM my_bag_table ORDER BY bagName")
    fun getAllBagNames(): LiveData<List<String>>

    @Query("SELECT bagId FROM my_bag_table WHERE bagName =:bagName LIMIT 1")
    fun getBagIdWithName(bagName: String): Int

    @Transaction
    @Query("SELECT * FROM my_bag_table WHERE bagId =:id")
    fun getItemsAndBagsInBagWithId(id: Int): BagWithItems?

    @Entity
    data class BagWithItems(
        @Embedded val bag: BagItemEntity,
        @Relation(
            parentColumn = "bagId",
            entityColumn = "bagOwnerId"
        )
        val items: List<InventoryItemEntity?>?
    )

    @Query("SELECT * from my_bag_table WHERE bagId = :key")
    fun getBagWithId(key: Long): LiveData<BagItemEntity>

    @Query("SELECT * FROM my_bag_table WHERE bagName =:name")
    fun findItemsByName(name: String): List<BagItemEntity>
}
