package com.fazemeright.myinventorytracker.usecase

import androidx.lifecycle.LiveData
import com.fazemeright.myinventorytracker.domain.models.Result
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import javax.inject.Inject

class GetAllBagNamesUseCase @Inject constructor(private val repository: InventoryRepository) {

    operator fun invoke(): LiveData<List<String>> {
        return repository.getAllBagNames()
    }
}