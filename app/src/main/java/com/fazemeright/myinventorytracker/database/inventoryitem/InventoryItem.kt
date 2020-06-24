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
    val itemId: Int = 0,

    var itemName: String = "",

    var itemDesc: String = "",

    var itemQuantity: Int = 1,

    @ColumnInfo(name = "bagOwnerId")
    var bagOwnerId: Int = 0
) : Serializable

@Entity
data class ItemWithBag(
    @Embedded var item: InventoryItem,
    @Relation(
        parentColumn = "bagOwnerId",
        entityColumn = "bagId"
    )
    var bag: BagItem
) : Serializable

//@Fts4(contentEntity = InventoryItem::class)
//@Entity(tableName = "inventoryItemFts")
//data class InventoryItemFts(val itemId: Int, val itemName: String, val itemDesc: String)