package com.fazemeright.myinventorytracker.database.inventoryitem

import androidx.room.*
import com.fazemeright.myinventorytracker.database.bag.BagItem

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
    val itemId: Int = 0,

    var itemName: String = "",

    var itemDesc: String = "",

    var itemQuantity: Int = 1,

    @ColumnInfo(name = "bagOwnerId")
    var bagOwnerId: Int = 0
)

@Entity
data class ItemWithBag(
    @Embedded var item: InventoryItem,
    @Relation(
        parentColumn = "bagOwnerId",
        entityColumn = "bagId"
    )
    var bag: BagItem
)

//@Fts4(contentEntity = InventoryItem::class)
//@Entity(tableName = "inventoryItemFts")
//data class InventoryItemFts(val itemId: Int, val itemName: String, val itemDesc: String)