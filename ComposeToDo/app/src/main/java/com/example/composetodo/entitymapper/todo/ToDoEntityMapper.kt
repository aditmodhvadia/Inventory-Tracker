package com.example.composetodo.entitymapper.todo

import com.example.composetodo.database.model.ToDoEntity
import com.example.composetodo.domain.todo.ToDo
import com.example.composetodo.entitymapper.IEntityMapper

object ToDoEntityMapper : IEntityMapper<ToDo, ToDoEntity> {
    override fun mapFromEntity(entity: ToDoEntity): ToDo {
        return ToDo(
            id = entity.id,
            createdAt = entity.createdAt,
            isCompleted = entity.isCompleted,
        )
    }

    override fun mapToEntity(domain: ToDo): ToDoEntity {
        return ToDoEntity(
            createdAt = domain.createdAt,
            isCompleted = domain.isCompleted,
        )
    }
}