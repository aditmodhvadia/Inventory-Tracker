package com.fazemeright.myinventorytracker.database.inventoryitem

import androidx.room.Dao
import androidx.room.Query

@Dao
interface InventoryItemFtsDao {

//    @Query("SELECT * FROM my_inventory_table JOIN inventoryItemFts ON my_inventory_table.itemId == inventoryItemFts.itemId WHERE inventoryItemFts.itemName MATCH :text GROUP BY inventoryItemFts.itemId")
//    fun inventoryItemsWithText(text: String): List<InventoryItemFts>
}