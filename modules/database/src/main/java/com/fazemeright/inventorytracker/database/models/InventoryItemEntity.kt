package com.fazemeright.inventorytracker.database.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Holds data for Inventory item, which can be stored in a [BagItemEntity]
 *
 * @author Adit Modhvadia
 * @since 2.1.1
 */
@Entity(
    tableName = "my_inventory_table",
    foreignKeys = [
        ForeignKey(
            entity = BagItemEntity::class,
            childColumns = ["bagOwnerId"],
            parentColumns = ["bagId"]
        )
    ]
)
@Parcelize
data class InventoryItemEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "itemId")
    val itemId: Int = 0,

    var itemName: String = "",

    var itemDesc: String = "",

    var itemQuantity: Int = 1,

    @ColumnInfo(name = "bagOwnerId")
    var bagOwnerId: Int = 0,
) : EntityModel, Parcelable

// @Fts4(contentEntity = InventoryItem::class)
// @Entity(tableName = "inventoryItemFts")
// data class InventoryItemFts(val itemId: Int, val itemName: String, val itemDesc: String)
