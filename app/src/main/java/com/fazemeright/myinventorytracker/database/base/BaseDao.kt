package com.fazemeright.myinventorytracker.database.base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface BaseDao<T> {
    /**
     * Inserts the given item into the database
     */
    @Insert
    fun insert(item: T)

    /**
     * Inserts the given items into the database
     */
    @Insert
    fun insertAll(item: List<T>)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param item new value to write
     */
    @Update
    fun update(item: T)

    /**
     * Deletes the given item from the database
     */
    @Delete
    fun deleteItem(item: T)
}