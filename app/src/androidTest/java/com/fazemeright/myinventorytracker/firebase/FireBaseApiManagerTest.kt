package com.fazemeright.myinventorytracker.firebase

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fazemeright.myinventorytracker.firebase.api.FireBaseApiManager
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runner.notification.Failure

@RunWith(AndroidJUnit4::class)
class FireBaseApiManagerTest {

    private val VALID_EMAIL = "dev.aditmodhvadia@gmail.com"
    private val VALID_PASSWORD = "12345678"
    private val INVALID_PASSWORD = "1234567890"

    @Test
    fun userIsSignedIn() = runBlocking {
        val result =
            FireBaseApiManager.signInWithEmailPassword(VALID_EMAIL, VALID_PASSWORD)
        result.await()
        assert(result.isSuccessful) {
            "Could not sign in User"
        }
        assertTrue(
            "User is not signed in",
            FireBaseApiManager.isUserSignedIn()
        )
        FireBaseApiManager.logout()
    }

    @Test
    fun userIsNotSignedIn() = runBlocking {
        assertFalse(
            "User is signed in without signing in first",
            FireBaseApiManager.isUserSignedIn()
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