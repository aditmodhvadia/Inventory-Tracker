package com.fazemeright.myinventorytracker.database.inventoryitem

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.fazemeright.myinventorytracker.database.bag.BagItem
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "my_inventory_table", foreignKeys = [ForeignKey(
        entity = BagItem::class,
        childColumns = ["bagOwnerId"],
        parentColumns = ["bagId"]
    )]
)
@Parcelize
data class InventoryItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "itemId")
    val itemId: Int = 0,

    var itemName: String = "",

    var itemDesc: String = "",

    var itemQuantity: Int = 1,

    @ColumnInfo(name = "bagOwnerId")
    var bagOwnerId: Int = 0
) : Parcelable

//@Fts4(contentEntity = InventoryItem::class)
//@Entity(tableName = "inventoryItemFts")
//data class InventoryItemFts(val itemId: Int, val itemName: String, val itemDesc: String)