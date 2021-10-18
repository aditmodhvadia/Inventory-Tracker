package com.example.composetodo.ui.todo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetodo.domain.todo.ToDo
import timber.log.Timber

@Composable
fun ToDoView(toDo: ToDo, isCheckClicked: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.0.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val textDecoration = if (toDo.isCompleted) {
            TextDecoration.LineThrough
        } else {
            TextDecoration.None
        }

        val textColor = if (toDo.isCompleted) {
            Color.Companion.DarkGray
        } else {
            Color.Companion.Black
        }
        Checkbox(checked = toDo.isCompleted, onCheckedChange = isCheckClicked)
        Box(modifier = Modifier.padding(4.0.dp))
        Text(
            text = toDo.text,
            style = TextStyle(
                textDecoration = textDecoration,
            ),
            color = textColor,
        )
    }
}

@Preview
@Composable
fun ToDoViewNotCheckedPreview() {
    ToDoView(toDo = ToDo(1L, text = "Preview text")) {
        Timber.d("Checked state changed to $it")
    }
}

@Preview
@Composable
fun ToDoViewCheckedPreview() {
    ToDoView(toDo = ToDo(1L, text = "Preview text", isCompleted = true)) {
        Timber.d("Checked state changed to $it")
    }
}