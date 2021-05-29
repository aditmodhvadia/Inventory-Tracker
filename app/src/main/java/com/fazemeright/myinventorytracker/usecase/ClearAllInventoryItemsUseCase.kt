package com.fazemeright.myinventorytracker.usecase

import com.fazemeright.myinventorytracker.repository.InventoryRepository
import javax.inject.Inject

class ClearAllInventoryItemsUseCase @Inject constructor(private val repository: InventoryRepository) {

    suspend operator fun invoke() {
        return repository.clearInventoryItems()
    }
}
