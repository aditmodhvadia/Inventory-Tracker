package com.fazemeright.myinventorytracker.usecase

import com.fazemeright.myinventorytracker.repository.InventoryRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

/**
 * Determine if [FirebaseUser] is logged in with [Result] of [Boolean].
 *
 * @param repository [InventoryRepository]
 * @author Adit Modhvadia
 * @since 2.1.1
 */
class IsUserSignedInUseCase @Inject constructor(private val repository: InventoryRepository) {

    operator fun invoke(): Result<Boolean> {
        return repository.isUserSignedIn()
    }
}
