package com.fazemeright.myinventorytracker.di.module

import com.fazemeright.myinventorytracker.repository.InventoryRepository
import com.fazemeright.myinventorytracker.usecase.IsUserSignedInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideIsUserSignedInUseCase(repository: InventoryRepository) =
        IsUserSignedInUseCase(repository)
}