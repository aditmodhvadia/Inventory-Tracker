package com.example.composetodo.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ToDo",
    indices = [
        Index(name = "is_completed-index", value = ["Is_Completed"]),
    ],
)
class ToDoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val id: Long = 0L,
    @ColumnInfo(name = "Created_At")
    val createdAt: String,
    @ColumnInfo(name = "Is_Completed", index = true)
    var isCompleted: Boolean,
    @ColumnInfo(name = "Text", index = true)
    var text: String,
) : IEntity