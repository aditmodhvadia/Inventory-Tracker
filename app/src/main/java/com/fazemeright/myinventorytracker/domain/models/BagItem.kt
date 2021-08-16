package com.fazemeright.myinventorytracker.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Holds data for a Bag, which can have many [InventoryItem]
 *
 * @author Adit Modhvadia
 * @since 2.1.1
 */
@Parcelize
data class BagItem(
    val bagId: Int = 0,
    var bagName: String = "",
    var bagColor: Int = 0,
    var bagDesc: String = "",
    var onlineId: String? = null
) : DomainModel, OnlineDatabaseStoreObject, Parcelable {
    override fun getOnlineDatabaseStoreId(): String? = onlineId

    override fun setOnlineDatabaseStoreId(onlineId: String) {
        this.onlineId = onlineId
    }
}
