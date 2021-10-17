package com.example.composetodo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.composetodo.database.dao.ToDoDao
import com.example.composetodo.database.model.ToDoEntity

@Database(entities = [ToDoEntity::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao


    companion object {
        fun getDataBase(applicationContext: Context): ToDoDatabase {
            return Room.databaseBuilder(
                applicationContext,
                ToDoDatabase::class.java,
                "todo_database"
            )
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    // CallBack when database is created
                })
                .build()
        }
    }
}