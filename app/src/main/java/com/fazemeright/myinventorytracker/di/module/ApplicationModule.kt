package com.fazemeright.myinventorytracker.di.module

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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
}
