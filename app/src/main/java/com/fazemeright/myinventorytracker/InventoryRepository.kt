package com.fazemeright.myinventorytracker

import com.fazemeright.myinventorytracker.database.bag.BagItemDao
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItemDao
import com.fazemeright.myinventorytracker.network.interfaces.SampleNetworkInterface
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    apiService: SampleNetworkInterface,
    bagItemDao: BagItemDao,
    inventoryItemDao: InventoryItemDao
)