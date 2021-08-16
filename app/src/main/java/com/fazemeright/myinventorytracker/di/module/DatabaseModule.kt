package com.fazemeright.myinventorytracker.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fazemeright.inventorytracker.database.InventoryDatabase
import com.fazemeright.inventorytracker.database.dao.BagItemDao
import com.fazemeright.inventorytracker.database.dao.InventoryItemDao
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
