package com.fazemeright.myinventorytracker.domain.database.offline.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fazemeright.myinventorytracker.domain.database.offline.room.dao.BagItemDao
import com.fazemeright.myinventorytracker.domain.database.offline.room.dao.InventoryItemDao
import com.fazemeright.myinventorytracker.domain.models.BagItem
import com.fazemeright.myinventorytracker.domain.models.InventoryItem
import javax.inject.Singleton

/**
 * A database that stores InventoryItem information.
 */
@Database(
    entities = [InventoryItem::class, BagItem::class/*, InventoryItemFts::class*/],
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
