package com.example.composetodo.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDao<T> {

    @Insert
    fun insert(data: T)

    @Insert
    fun insertAll(vararg data: T)

    @Update
    fun update(data: T)

    @Delete
    fun delete(data: T)
}