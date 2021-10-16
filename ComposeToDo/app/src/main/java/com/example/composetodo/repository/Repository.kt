package com.example.composetodo.repository

import androidx.lifecycle.LiveData
import com.example.composetodo.database.ToDoDatabase
import com.example.composetodo.domain.todo.ToDo

interface Repository {
    suspend fun insert(toDo: ToDo)
    fun getAllToDos(): LiveData<List<ToDo>>

    val database:ToDoDatabase
}
