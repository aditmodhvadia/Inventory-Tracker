package com.example.composetodo.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.composetodo.repository.InMemoryRepository
import com.example.composetodo.ui.theme.ComposeToDoTheme
import javax.inject.Inject

class ToDoListFragment : Fragment() {

    @Inject
    lateinit var inMemoryRepository: InMemoryRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ComposeToDoTheme {
                    Surface {
                        ToDoListView(inMemoryRepository)
                    }
                }
            }
        }
    }
}

@Composable
fun ToDoListView(inMemoryRepository: InMemoryRepository) {
    inMemoryRepository.toDoList.value?.map {
        LazyColumn {
            ToDoView(toDo = it, isCheckClicked = {})
        }
    }
}