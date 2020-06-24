package com.fazemeright.myinventorytracker.di.module

import com.fazemeright.myinventorytracker.database.bag.BagItem
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.database.inventoryitem.ItemWithBag
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
    fun provideSelectedItem(inventoryItem: InventoryItem, bagItem: BagItem): ItemWithBag =
        ItemWithBag(item = inventoryItem, bag = bagItem)

    @Provides
    @Singleton
    fun provideInventoryItem(): InventoryItem =
        InventoryItem()

    @Provides
    @Singleton
    fun provideBagItem(): BagItem =
        BagItem()


}