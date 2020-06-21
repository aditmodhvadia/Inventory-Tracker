package com.fazemeright.myinventorytracker.database.inventoryitem

import androidx.room.*
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

@Entity
data class ItemWithBag(
    @Embedded val item: InventoryItem,
    @Relation(
        parentColumn = "bagOwnerId",
        entityColumn = "bagId"
    )
    val bag: BagItem
) : Serializable

//@Fts4(contentEntity = InventoryItem::class)
//@Entity(tableName = "inventoryItemFts")
//data class InventoryItemFts(val itemId: Int, val itemName: String, val itemDesc: String)