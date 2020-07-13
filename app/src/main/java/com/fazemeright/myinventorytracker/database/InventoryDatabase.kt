package com.fazemeright.myinventorytracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fazemeright.myinventorytracker.database.bag.BagItem
import com.fazemeright.myinventorytracker.database.bag.BagItemDao
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItemDao
import javax.inject.Singleton

/**
 * A database that stores InventoryItem information.
 */
@Database(
    entities = [InventoryItem::class, BagItem::class/*, InventoryItemFts::class*/],
    version = 6,
    exportSchema = false
)
@Singleton
abstract class InventoryDatabase :
    RoomDatabase() {

    abstract val inventoryItemDao: InventoryItemDao

    abstract val bagItemDao: BagItemDao

//    abstract val inventoryItemFtsDao: InventoryItemFtsDao
}
