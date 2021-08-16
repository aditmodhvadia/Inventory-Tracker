package com.fazemeright.myinventorytracker.domain.mappers.entity

import com.fazemeright.inventorytracker.database.models.ItemWithBagEntity
import com.fazemeright.myinventorytracker.domain.models.ItemWithBag

object ItemWithBagEntityMapper : EntityMapper<ItemWithBag, ItemWithBagEntity> {
    override fun mapToEntity(domainModel: ItemWithBag): ItemWithBagEntity {
        return ItemWithBagEntity(
            item = InventoryItemEntityMapper.mapToEntity(domainModel.item),
            bag = BagItemEntityMapper.mapToEntity(domainModel.bag),
        )
    }

    override fun mapFromEntity(entityModel: ItemWithBagEntity): ItemWithBag {
        return ItemWithBag(
            item = InventoryItemEntityMapper.mapFromEntity(entityModel.item),
            bag = BagItemEntityMapper.mapFromEntity(entityModel.bag),
        )
    }
}