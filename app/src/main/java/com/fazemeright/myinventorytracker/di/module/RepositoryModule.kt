package com.fazemeright.myinventorytracker.di.module

import com.fazemeright.myinventorytracker.domain.database.offline.room.dao.BagItemDao
import com.fazemeright.myinventorytracker.domain.database.offline.room.dao.InventoryItemDao
import com.fazemeright.myinventorytracker.network.interfaces.SampleNetworkInterface
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
//    @ActivityRetainedScoped
    @Singleton
    fun provideInventoryRepository(
        bagItemDao: BagItemDao,
        inventoryItemDao: InventoryItemDao,
        apiService: SampleNetworkInterface
    ): InventoryRepository {
        return InventoryRepository(bagItemDao, inventoryItemDao, apiService)
    }
}