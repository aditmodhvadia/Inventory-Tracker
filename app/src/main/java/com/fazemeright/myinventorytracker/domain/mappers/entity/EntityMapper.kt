package com.fazemeright.myinventorytracker.domain.mappers.entity

import com.fazemeright.inventorytracker.database.models.EntityModel
import com.fazemeright.myinventorytracker.domain.models.DomainModel

interface EntityMapper<Domain : DomainModel, Entity : EntityModel> {
    fun mapToEntity(domainModel: Domain): Entity
    fun mapFromEntity(entityModel: Entity): Domain
}