package com.fazemeright.myinventorytracker.firebase

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fazemeright.myinventorytracker.firebase.api.FireBaseApiManager
import com.fazemeright.myinventorytracker.firebase.models.Failure
import com.fazemeright.myinventorytracker.firebase.models.Success
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FireBaseApiManagerTest {

    private val VALID_EMAIL = "dev.aditmodhvadia@gmail.com"
    private val VALID_PASSWORD = "12345678"
    private val INVALID_PASSWORD = "1234567890"

    @Test
    fun userIsSignedIn() = runBlocking {
        val result =
            FireBaseApiManager.signInWithEmailPassword(VALID_EMAIL, VALID_PASSWORD)
        assert(result is Success<FirebaseUser>) {
            "Could not sign in User"
        }
        assertTrue("User is not signed in", (FireBaseApiManager.isUserSignedIn() as Success).data)
    }

    @Test
    fun userIsNotSignedIn() = runBlocking {
        assertFalse(
            "User is signed in without signing in first",
            FireBaseApiManager.isUserSignedIn() is Failure
        )
        val result =
            FireBaseApiManager.signInWithEmailPassword(VALID_EMAIL, INVALID_PASSWORD)
        assert(result is Failure) {
            "User was signed in successfully despite incorrect credentials"
        }
    }


    /*@Test
    fun registerNewUserSuccessfully() = runBlocking {
        val result =
            FireBaseApiManager.registerWithEmailPassword(VALID_EMAIL, VALID_PASSWORD)

        assertNotNull((result as Success).data.toString()) {
            println("Error occurred")
        }
    }*/
}