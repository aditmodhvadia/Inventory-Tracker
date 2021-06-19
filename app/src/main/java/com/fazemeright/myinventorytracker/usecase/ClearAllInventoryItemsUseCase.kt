package com.fazemeright.myinventorytracker.usecase

import com.fazemeright.myinventorytracker.domain.models.InventoryItem
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import javax.inject.Inject

/**
 * Clear all [InventoryItem].
 *
 * @param repository [InventoryRepository]
 * @author Adit Modhvadia
 * @since 2.1.1
 */
class ClearAllInventoryItemsUseCase @Inject constructor(private val repository: InventoryRepository) {

    suspend operator fun invoke() {
        return repository.clearInventoryItems()
    }
}
