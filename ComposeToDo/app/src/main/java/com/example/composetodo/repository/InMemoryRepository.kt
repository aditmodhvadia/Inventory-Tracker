package com.example.composetodo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.composetodo.database.ToDoDatabase
import com.example.composetodo.domain.todo.ToDo

object InMemoryRepository : Repository {
    private val _toDoList = MutableLiveData(
        listOf(
            ToDo(id = 1L, text = "Sample Todo 1"),
            ToDo(id = 2L, text = "Sample Todo 2"),
            ToDo(id = 3L, text = "Sample Todo 3"),
            ToDo(id = 4L, text = "Sample Todo 4"),
            ToDo(id = 5L, text = "Sample Todo 5"),
        )
    )
    val toDoList: LiveData<List<ToDo>> = _toDoList

    override suspend fun insert(toDo: ToDo) {
        _toDoList.postValue(
            listOf(
                *(_toDoList.value?.toTypedArray() ?: emptyArray()),
                toDo
            )
        )
    }

    override fun getAllToDos(): LiveData<List<ToDo>> {
        return toDoList
    }

    override val database: ToDoDatabase
        get() = TODO("Not yet implemented")
}