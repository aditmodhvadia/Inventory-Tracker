package com.fazemeright.myinventorytracker.domain.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "my_bag_table")
data class BagItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bagId")
    val bagId: Int = 0,

    var bagName: String = "",

    var bagColor: Int = 0,

    var bagDesc: String = "",
    var onlineId: String? = null
) : OnlineDatabaseStoreObject, Parcelable {
    override fun getOnlineDatabaseStoreId(): String? = onlineId

    override fun setOnlineDatabaseStoreId(onlineId: String) {
        this.onlineId = onlineId
    }
}