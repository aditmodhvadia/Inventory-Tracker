package com.example.composetodo.di

import com.example.composetodo.database.ToDoDatabase
import com.example.composetodo.repository.Repository
import com.example.composetodo.repository.ToDoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideToDoRepository(database: ToDoDatabase): Repository {
        return ToDoRepository(database)
    }
}