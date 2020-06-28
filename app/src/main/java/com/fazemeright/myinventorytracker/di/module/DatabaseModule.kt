package com.fazemeright.myinventorytracker.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fazemeright.myinventorytracker.database.InventoryDatabase
import com.fazemeright.myinventorytracker.database.bag.BagItemDao
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItemDao
import com.fazemeright.myinventorytracker.utils.TestUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
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
            // Wipes and rebuilds instead of migrating if no Migration object.
            // Migration is not part of this lesson. You can learn more about
            // migration with Room in this blog post:
            // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // moving to a new thread
//                                Temporary add dummy bag data
                    CoroutineScope(Dispatchers.IO).launch {
                        val items = listOf(
                            TestUtils.getBagItem(1),
                            TestUtils.getBagItem(2),
                            TestUtils.getBagItem(3)
                        )
                        provideDatabase(context).bagItemDao
                            .insertAll(
                                items
                            )
                        Timber.d("Sample Bag data inserted")
                    }
                }
            })
            .build()
    }

}