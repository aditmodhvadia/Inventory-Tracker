package com.example.composetodo.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetodo.domain.todo.ToDo
import com.example.composetodo.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(private val toDoRepository: Repository) : ViewModel() {

    val toDos: LiveData<List<ToDo>> = toDoRepository.getAllToDos()

    fun insertToDo(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.insert(toDo)
        }
    }
}