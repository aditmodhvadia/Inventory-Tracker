package com.fazemeright.myinventorytracker.domain.database.offline.room.dao

import androidx.room.*

@Dao
interface BaseDao<T> {
    /**
     * Inserts the given item into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: T): Long

    /**
     * Inserts the given items into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
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