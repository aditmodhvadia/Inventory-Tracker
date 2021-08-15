package com.fazemeright.myinventorytracker.di.module

import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkerFactory
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
    fun getWorkMangerConfiguration(workerFactory: HiltWorkerFactory): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
