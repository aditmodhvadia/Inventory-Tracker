package com.fazemeright.inventorytracker.database.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Holds data for a Bag, which can have many [InventoryItemEntity]
 *
 * @author Adit Modhvadia
 * @since 2.1.1
 */
@Parcelize
@Entity(tableName = "my_bag_table")
data class BagItemEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bagId")
    val bagId: Int = 0,

    var bagName: String = "",

    var bagColor: Int = 0,

    var bagDesc: String = "",
) : EntityModel, Parcelable
