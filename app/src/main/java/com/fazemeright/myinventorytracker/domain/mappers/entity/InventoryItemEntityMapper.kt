package com.fazemeright.myinventorytracker.domain.mappers.entity

import com.fazemeright.inventorytracker.database.models.InventoryItemEntity
import com.fazemeright.myinventorytracker.domain.models.InventoryItem

object InventoryItemEntityMapper : EntityMapper<InventoryItem, InventoryItemEntity> {
    override fun mapToEntity(domainModel: InventoryItem): InventoryItemEntity {
        return InventoryItemEntity(
            itemId = domainModel.itemId,
            itemName = domainModel.itemName,
            itemDesc = domainModel.itemDesc,
            itemQuantity = domainModel.itemQuantity,
            bagOwnerId = domainModel.bagOwnerId,
        )
    }

    override fun mapFromEntity(entityModel: InventoryItemEntity): InventoryItem {
        return InventoryItem(
            itemId = entityModel.itemId,
            itemName = entityModel.itemName,
            itemDesc = entityModel.itemDesc,
            itemQuantity = entityModel.itemQuantity,
            bagOwnerId = entityModel.bagOwnerId,
        )
    }

    override fun InventoryItem.mapDomainToEntity(): InventoryItemEntity = mapToEntity(this)
}
