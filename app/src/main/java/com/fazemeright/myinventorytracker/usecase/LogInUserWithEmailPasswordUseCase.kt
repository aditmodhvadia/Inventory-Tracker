package com.fazemeright.myinventorytracker.usecase

import com.fazemeright.myinventorytracker.domain.models.Result
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

/**
 * Log in user with email and password and get [Result] of [FirebaseUser].
 *
 * @param repository [InventoryRepository]
 * @author Adit Modhvadia
 * @since 2.1.1
 */
class LogInUserWithEmailPasswordUseCase @Inject constructor(private val repository: InventoryRepository) {

    suspend operator fun invoke(email: String, password: String): Result<FirebaseUser> {
        return repository.performLogin(email, password)
    }
}
