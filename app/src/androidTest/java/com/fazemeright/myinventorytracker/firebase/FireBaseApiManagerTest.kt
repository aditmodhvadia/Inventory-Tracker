package com.fazemeright.myinventorytracker.firebase

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fazemeright.myinventorytracker.database.bag.BagItem
import com.fazemeright.myinventorytracker.firebase.api.FireBaseApiManager
import com.fazemeright.myinventorytracker.firebase.models.Result
import com.fazemeright.myinventorytracker.utils.TestUtils
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runner.notification.Failure

@RunWith(AndroidJUnit4::class)
class FireBaseApiManagerTest {

    private val VALID_EMAIL = "dev.aditmodhvadia@gmail.com"
    private val VALID_PASSWORD = "12345678"
    private val INVALID_PASSWORD = "1234567890"

    @After
    fun tearDown() {
        FireBaseApiManager.logout()
    }

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

    @Test
    fun addInventoryItemToDatabaseWithSignIn() = runBlocking {
        FireBaseApiManager.signIn(VALID_EMAIL, VALID_PASSWORD).await()
        val item = TestUtils.getInventoryItem(-1, -1, "TestItem for FireStore")
        when (val result = FireBaseApiManager.storeInventoryItem(item)) {
            is Result.Success<Boolean> -> {
                FireBaseApiManager.deleteInventoryItem(item)
                assertTrue(result.data)
            }
            is Result.Error -> {
                throw AssertionError(result.msg)
            }
        }
    }

    @Test
    fun addInventoryItemToDatabaseWithoutSignIn() = runBlocking {
        val item = TestUtils.getInventoryItem(-1, -1, "TestItem for FireStore")
        when (val result = FireBaseApiManager.storeInventoryItem(item)) {
            is Result.Success<Boolean> -> {
                FireBaseApiManager.deleteInventoryItem(item)
                throw AssertionError("Inventory Item added to database successfully without sign in")
            }
            is Result.Error -> {
                assertEquals("User not signed in!", result.msg)
            }
        }
    }

    @Test
    fun addBagToDatabaseWithSignIn() = runBlocking {
        FireBaseApiManager.signIn(VALID_EMAIL, VALID_PASSWORD).await()
        val item = TestUtils.getBagItem(-1)
        when (val result = FireBaseApiManager.storeBag(item)) {
            is Result.Success<Boolean> -> {
                FireBaseApiManager.deleteBag(item)
                assertTrue(result.data)
            }
            is Result.Error -> {
                throw AssertionError(result.msg)
            }
        }
    }

    @Test
    fun addBagToDatabaseWithoutSignIn() = runBlocking {
        val item = TestUtils.getBagItem(-1)
        when (val result = FireBaseApiManager.storeBag(item)) {
            is Result.Success<Boolean> -> {
                FireBaseApiManager.deleteBag(item)
                throw AssertionError("Bag added to database successfully without sign in")
            }
            is Result.Error -> {
                assertEquals("User not signed in!", result.msg)
            }
        }
    }

//    TODO: Test batch write
//    TODO: Test read all items

    @Test
    fun batchWriteBagItemsFromLocal() = runBlocking {
        FireBaseApiManager.signIn(VALID_EMAIL, VALID_PASSWORD).await()
        val bagItems = (-10..-1).map { TestUtils.getBagItem(id = it) }

        FireBaseApiManager.batchWriteBags(bagItems).await()

        when (val result = FireBaseApiManager.getAllBags()) {
            is Result.Success<List<BagItem>> -> {
                bagItems.forEach { item ->
                    FireBaseApiManager.deleteBag(item)
                }
                assertTrue(result.data.toSet().containsAll(bagItems))
            }
            is Result.Error -> {
                bagItems.forEach { item ->
                    FireBaseApiManager.deleteBag(item)
                }
                throw AssertionError("Bag added to database successfully without sign in")
            }
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