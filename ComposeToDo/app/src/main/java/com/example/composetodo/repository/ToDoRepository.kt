package com.example.composetodo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.composetodo.database.ToDoDatabase
import com.example.composetodo.domain.todo.ToDo
import com.example.composetodo.entitymapper.todo.ToDoEntityMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ToDoRepository @Inject constructor(override val database: ToDoDatabase) : Repository {
    override suspend fun insert(toDo: ToDo) {
        withContext(Dispatchers.IO) {
            database.toDoDao().insert(ToDoEntityMapper.mapToEntity(toDo))
        }
    }

    override fun getAllToDos(): LiveData<List<ToDo>> {
        return database.toDoDao().getAll().map {
            it.map { toDoEntity ->
                ToDoEntityMapper.mapFromEntity(toDoEntity)
            }
        }
    }
}