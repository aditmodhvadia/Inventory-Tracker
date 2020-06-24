package com.fazemeright.myinventorytracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fazemeright.myinventorytracker.database.bag.BagItem
import com.fazemeright.myinventorytracker.database.bag.BagItemDao
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItemDao

/**
 * A database that stores InventoryItem information.
 * And a global method to get access to the database.
 *
 * This pattern is pretty much the same for any database,
 * so you can reuse it.
 */
@Database(
    entities = [InventoryItem::class, BagItem::class/*, InventoryItemFts::class*/],
    version = 6,
    exportSchema = false
)
abstract class InventoryDatabase :
    RoomDatabase() {

    abstract val inventoryItemDao: InventoryItemDao

    abstract val bagItemDao: BagItemDao

//    abstract val inventoryItemFtsDao: InventoryItemFtsDao
}
