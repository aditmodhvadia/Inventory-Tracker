package com.fazemeright.myinventorytracker.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fazemeright.myinventorytracker.domain.database.offline.room.InventoryDatabase
import com.fazemeright.myinventorytracker.domain.database.offline.room.dao.BagItemDao
import com.fazemeright.myinventorytracker.domain.database.offline.room.dao.InventoryItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideBagItemDao(database: InventoryDatabase): BagItemDao {
        return database.bagItemDao
    }

    @Provides
    fun provideInventoryItemDao(database: InventoryDatabase): InventoryItemDao {
        return database.inventoryItemDao
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): InventoryDatabase {
        return Room.databaseBuilder(
            context,
            InventoryDatabase::class.java,
            "inventory_list_database"
        )
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
            })
            .build()
    }
}
