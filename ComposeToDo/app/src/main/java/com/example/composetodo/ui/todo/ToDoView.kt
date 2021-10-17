package com.example.composetodo.ui.todo

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetodo.domain.todo.ToDo
import timber.log.Timber

@Composable
fun ToDoView(toDo: ToDo, isCheckClicked: (Boolean) -> Unit) {
    Row {
        Checkbox(checked = toDo.isCompleted, onCheckedChange = isCheckClicked)
        Text(text = toDo.text)
    }
}

@Preview
@Composable
fun ToDoViewPreview() {
    ToDoView(toDo = ToDo(1L, text = "Preview text")) {
        Timber.d("Checked state changed to $it")
    }
}