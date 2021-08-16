package com.fazemeright.inventorytracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fazemeright.inventorytracker.database.dao.BagItemDao
import com.fazemeright.inventorytracker.database.dao.InventoryItemDao
import com.fazemeright.inventorytracker.database.models.BagItemEntity
import com.fazemeright.inventorytracker.database.models.InventoryItemEntity
import javax.inject.Singleton

/**
 * A database that stores InventoryItem information.
 */
@Database(
    entities = [InventoryItemEntity::class, BagItemEntity::class/*, InventoryItemFts::class*/],
    version = 8,
    exportSchema = false
)
@Singleton
abstract class InventoryDatabase :
    RoomDatabase() {

    abstract val inventoryItemDao: InventoryItemDao

    abstract val bagItemDao: BagItemDao

//    abstract val inventoryItemFtsDao: InventoryItemFtsDao
}
