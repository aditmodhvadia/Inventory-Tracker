package com.fazemeright.myinventorytracker.di.module

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.fazemeright.myinventorytracker.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideApplication(@ApplicationContext app: Context): App {
        return app as App
    }

    @Provides
    @Singleton
    fun getWorkMangerConfiguration(workerFactory: HiltWorkerFactory): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
