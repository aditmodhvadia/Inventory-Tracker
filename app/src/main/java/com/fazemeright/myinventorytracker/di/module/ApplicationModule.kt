package com.fazemeright.myinventorytracker.di.module

import com.fazemeright.myinventorytracker.database.bag.BagItem
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.database.inventoryitem.ItemWithBag
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    /*@Provides
    @Singleton
    fun getRepository(
        apiService: SampleNetworkInterface,
        bagItemDao: BagItemDao,
        inventoryItemDao: InventoryItemDao
    ): InventoryRepository = InventoryRepository(bagItemDao, inventoryItemDao, apiService)*/

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
