package com.example.composetodo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composetodo.database.dao.ToDoDao
import com.example.composetodo.database.model.ToDoEntity

@Database(entities = [ToDoEntity::class], version = 1)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}