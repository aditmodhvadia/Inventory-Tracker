package com.fazemeright.myinventorytracker.di.module

import com.fazemeright.myinventorytracker.domain.database.offline.room.dao.BagItemDao
import com.fazemeright.myinventorytracker.domain.database.offline.room.dao.InventoryItemDao
import com.fazemeright.myinventorytracker.network.interfaces.SampleNetworkInterface
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {
    @Provides
    @ActivityRetainedScoped
    fun provideInventoryRepository(
        bagItemDao: BagItemDao,
        inventoryItemDao: InventoryItemDao,
        apiService: SampleNetworkInterface
    ): InventoryRepository {
        return InventoryRepository(bagItemDao, inventoryItemDao, apiService)
    }
}