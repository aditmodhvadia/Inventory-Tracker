package com.fazemeright.myinventorytracker.database.bag

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "my_bag_table")
data class BagItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bagId")
    val bagId: Int = 0,

    var bagName: String = "",

    var bagColor: Int = 0,

    var bagDesc: String = ""
) : Serializable