package com.fazemeright.myinventorytracker.database.inventoryitem

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.fazemeright.myinventorytracker.database.bag.BagItem
import java.io.Serializable

@Entity(
    tableName = "my_inventory_table", foreignKeys = [ForeignKey(
        entity = BagItem::class,
        childColumns = ["itemId"],
        parentColumns = ["bagId"]
    )]
)
data class InventoryItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "itemId")
    val itemId: Int,

    var itemName: String,

    var itemDesc: String = "",

    var itemQuantity: Int = 1,

    @ColumnInfo(name = "bagOwnerId")
    var bagOwnerId: Int
) : Serializable
