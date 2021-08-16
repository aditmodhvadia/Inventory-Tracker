package com.fazemeright.inventorytracker.database.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

/**
 * Holds and [InventoryItemEntity] and the [BagItemEntity] it is in.
 *
 * @author Adit Modhvadia
 * @since 2.1.1
 */
@Parcelize
@Entity
data class ItemWithBagEntity(
    @Embedded var item: InventoryItemEntity,
    @Relation(
        parentColumn = "bagOwnerId",
        entityColumn = "bagId"
    )
    var bag: BagItemEntity
) : EntityModel, Parcelable
