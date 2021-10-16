package com.example.composetodo.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.composetodo.database.model.ToDoEntity

interface ToDoDao : BaseDao<ToDoEntity> {

    @Query("SELECT * From ToDo")
    fun getAll(): LiveData<List<ToDoEntity>>
}