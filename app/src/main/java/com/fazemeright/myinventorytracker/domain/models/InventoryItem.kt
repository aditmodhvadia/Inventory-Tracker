package com.fazemeright.myinventorytracker.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Holds data for Inventory item, which can be stored in a [BagItem]
 *
 * @author Adit Modhvadia
 * @since 2.1.1
 */
@Parcelize
data class InventoryItem(
    val itemId: Int = 0,

    var itemName: String = "",

    var itemDesc: String = "",

    var itemQuantity: Int = 1,

    var bagOwnerId: Int = 0,
    var onlineId: String? = null
) : DomainModel, OnlineDatabaseStoreObject, Parcelable {
    override fun getOnlineDatabaseStoreId(): String? = onlineId

    override fun setOnlineDatabaseStoreId(onlineId: String) {
        this.onlineId = onlineId
    }
}

// @Fts4(contentEntity = InventoryItem::class)
// @Entity(tableName = "inventoryItemFts")
// data class InventoryItemFts(val itemId: Int, val itemName: String, val itemDesc: String)
