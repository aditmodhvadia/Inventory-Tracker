package com.example.composetodo.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetodo.domain.todo.ToDo
import com.example.composetodo.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(private val toDoRepository: Repository) : ViewModel() {

    val toDos: LiveData<List<ToDo>> = toDoRepository.getAllToDos()

    fun insertToDo(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.insert(toDo)
        }
    }
}