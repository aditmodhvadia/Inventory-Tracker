package com.fazemeright.myinventorytracker.domain.mappers.entity

import com.fazemeright.inventorytracker.database.models.BagItemEntity
import com.fazemeright.myinventorytracker.domain.models.BagItem

object BagItemEntityMapper : EntityMapper<BagItem, BagItemEntity> {
    override fun mapToEntity(domainModel: BagItem): BagItemEntity {
        return BagItemEntity(
            bagId = domainModel.bagId,
            bagName = domainModel.bagName,
            bagColor = domainModel.bagColor,
            bagDesc = domainModel.bagDesc
        )
    }

    override fun mapFromEntity(entityModel: BagItemEntity): BagItem {
        return BagItem(
            bagId = entityModel.bagId,
            bagName = entityModel.bagName,
            bagColor = entityModel.bagColor,
            bagDesc = entityModel.bagDesc
        )
    }
}