package com.fazemeright.myinventorytracker.usecase

import com.fazemeright.myinventorytracker.domain.models.Result
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import javax.inject.Inject

class IsUserSignedInUseCase @Inject constructor(private val repository: InventoryRepository) {

    operator fun invoke(): Result<Boolean> {
        return repository.isUserSignedIn()
    }
}
