package com.example.composetodo.domain.todo

import com.example.composetodo.domain.IDomain

data class ToDo(
    val id: Long,
    val createdAt: String = System.currentTimeMillis().toString(),
    var isCompleted: Boolean = false,
    var text: String,
) : IDomain
