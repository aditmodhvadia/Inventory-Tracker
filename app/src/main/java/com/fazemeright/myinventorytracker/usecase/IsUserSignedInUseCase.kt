package com.fazemeright.myinventorytracker.usecase

import com.fazemeright.myinventorytracker.domain.models.Result
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Scope

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
