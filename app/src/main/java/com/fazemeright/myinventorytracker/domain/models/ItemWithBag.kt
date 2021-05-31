package com.fazemeright.myinventorytracker.domain.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

/**
 * Holds and [InventoryItem] and the [BagItem] it is in.
 *
 * @author Adit Modhvadia
 * @since 2.1.1
 */
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
