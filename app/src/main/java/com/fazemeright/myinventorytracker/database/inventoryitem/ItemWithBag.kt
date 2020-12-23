package com.fazemeright.myinventorytracker.database.inventoryitem

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.fazemeright.myinventorytracker.database.bag.BagItem
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class ItemWithBag(
    @Embedded var item: InventoryItem,
    @Relation(
        parentColumn = "bagOwnerId",
        entityColumn = "bagId"
    )
    var bag: BagItem
) : Parcelable