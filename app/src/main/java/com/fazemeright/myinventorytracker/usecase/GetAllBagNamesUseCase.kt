package com.fazemeright.myinventorytracker.usecase

import androidx.lifecycle.LiveData
import com.fazemeright.myinventorytracker.domain.models.BagItem
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import javax.inject.Inject


/**
 * Get all [BagItem.bagName].
 *
 * @param repository [InventoryRepository]
 * @author Adit Modhvadia
 * @since 2.1.1
 */
class GetAllBagNamesUseCase @Inject constructor(private val repository: InventoryRepository) {

    operator fun invoke(): LiveData<List<String>> {
        return repository.getAllBagNames()
    }
}
