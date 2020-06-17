package com.fazemeright.myinventorytracker.database.inventoryitem

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_inventory_table")
data class InventoryItem(
    @PrimaryKey(autoGenerate = true)
    var itemId: Long = 0L,

    var itemName: String,

    var itemDesc: String,

    var itemQuantity: Int,

    var bagId: Long
)
