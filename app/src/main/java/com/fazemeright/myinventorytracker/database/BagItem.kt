package com.fazemeright.myinventorytracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "my_bag_table")
data class BagItem(
    @PrimaryKey(autoGenerate = true)
    var bagId: Long = 0L,

    var bagName: String,

    var bagColor: Int,

    var bagDesc: String
) : Serializable