package com.example.composetodo.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.composetodo.database.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideToDoDatabase(@ApplicationContext applicationContext: Context): ToDoDatabase {
        return Room.databaseBuilder(
            applicationContext,
            ToDoDatabase::class.java,
            "todo_database"
        )
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                // CallBack when database is created
            })
            .build()
    }
}