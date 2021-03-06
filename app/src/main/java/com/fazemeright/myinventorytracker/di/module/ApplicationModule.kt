package com.fazemeright.myinventorytracker.di.module

import com.fazemeright.myinventorytracker.InventoryRepository
import com.fazemeright.myinventorytracker.database.bag.BagItem
import com.fazemeright.myinventorytracker.database.bag.BagItemDao
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItemDao
import com.fazemeright.myinventorytracker.database.inventoryitem.ItemWithBag
import com.fazemeright.myinventorytracker.network.interfaces.SampleNetworkInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun getRepository(
        apiService: SampleNetworkInterface,
        bagItemDao: BagItemDao,
        inventoryItemDao: InventoryItemDao
    ): InventoryRepository = InventoryRepository(apiService, bagItemDao, inventoryItemDao)


    @Provides
    @Singleton
    fun provideSelectedItem(inventoryItem: InventoryItem, bagItem: BagItem): ItemWithBag =
        ItemWithBag(item = inventoryItem, bag = bagItem)

    @Provides
    fun provideInventoryItem(): InventoryItem =
        InventoryItem()

    @Provides
    fun provideBagItem(): BagItem =
        BagItem()
}
