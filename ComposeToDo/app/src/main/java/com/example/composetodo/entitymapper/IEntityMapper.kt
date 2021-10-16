package com.example.composetodo.entitymapper

import com.example.composetodo.database.model.IEntity
import com.example.composetodo.domain.IDomain

interface IEntityMapper<Domain : IDomain, Entity : IEntity> {

    fun mapFromEntity(entity: Entity): Domain

    fun mapToEntity(domain: Domain): Entity
}