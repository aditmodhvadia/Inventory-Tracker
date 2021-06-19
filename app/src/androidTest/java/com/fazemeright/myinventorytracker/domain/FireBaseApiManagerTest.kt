package com.fazemeright.myinventorytracker.domain

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fazemeright.myinventorytracker.domain.authentication.firebase.FireBaseUserAuthentication
import com.fazemeright.myinventorytracker.domain.database.online.firestore.FireBaseOnlineDatabaseStore
import com.fazemeright.myinventorytracker.domain.models.BagItem
import com.fazemeright.myinventorytracker.domain.models.Result
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

    @After
    fun tearDown() {
        FireBaseUserAuthentication.logout()
    }

    @Test
    fun userIsSignedIn() = runBlocking {
        val result =
            FireBaseUserAuthentication.signIn(VALID_EMAIL, VALID_PASSWORD)
        result.await()
        assert(result.isSuccessful) {
            "Could not sign in User"
        }
        assertTrue(
            "User is not signed in",
            FireBaseUserAuthentication.isUserSignedIn()
        )
        FireBaseUserAuthentication.logout()
    }

    @Test
    fun userIsNotSignedIn() = runBlocking {
        assertFalse(
            "User is signed in without signing in first",
            FireBaseUserAuthentication.isUserSignedIn()
        )
        val result =
            FireBaseUserAuthentication.signIn(VALID_EMAIL, INVALID_PASSWORD)
        assert(result is Failure) {
            "User was signed in successfully despite incorrect credentials"
        }
    }

    @Test
    fun addInventoryItemToDatabaseWithSignIn() = runBlocking {
        FireBaseUserAuthentication.signIn(VALID_EMAIL, VALID_PASSWORD).await()
        val item = TestUtils.getInventoryItem(-1, -1, "TestItem for FireStore")
        when (val result = FireBaseOnlineDatabaseStore.storeInventoryItem(item)) {
            is Result.Success<Boolean> -> {
                FireBaseOnlineDatabaseStore.deleteInventoryItem(item)
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
        when (val result = FireBaseOnlineDatabaseStore.storeInventoryItem(item)) {
            is Result.Success<Boolean> -> {
                FireBaseOnlineDatabaseStore.deleteInventoryItem(item)
                throw AssertionError("Inventory Item added to database successfully without sign in")
            }
            is Result.Error -> {
                assertEquals("User not signed in!", result.msg)
            }
        }
    }

    @Test
    fun addBagToDatabaseWithSignIn() = runBlocking {
        FireBaseUserAuthentication.signIn(VALID_EMAIL, VALID_PASSWORD).await()
        val item = TestUtils.getBagItem(-1)
        when (val result = FireBaseOnlineDatabaseStore.storeBag(item)) {
            is Result.Success<Boolean> -> {
                FireBaseOnlineDatabaseStore.deleteBag(item)
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
        when (val result = FireBaseOnlineDatabaseStore.storeBag(item)) {
            is Result.Success<Boolean> -> {
                FireBaseOnlineDatabaseStore.deleteBag(item)
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
        FireBaseUserAuthentication.signIn(VALID_EMAIL, VALID_PASSWORD).await()
        val bagItems = (-10..-1).map { TestUtils.getBagItem(id = it) }

        FireBaseOnlineDatabaseStore.batchWriteBags(bagItems).await()

        when (val result = FireBaseOnlineDatabaseStore.getAllBags()) {
            is Result.Success<List<BagItem>> -> {
                bagItems.forEach { item ->
                    FireBaseOnlineDatabaseStore.deleteBag(item)
                }
                assertTrue(result.data.toSet().containsAll(bagItems))
            }
            is Result.Error -> {
                bagItems.forEach { item ->
                    FireBaseOnlineDatabaseStore.deleteBag(item)
                }
                throw AssertionError("Bag added to database successfully without sign in")
            }
        }
    }

    /*@Test
    fun registerNewUserSuccessfully() = runBlocking {
        val result =
            FireBaseUserAuthentication.registerWithEmailPassword(VALID_EMAIL, VALID_PASSWORD)

        assertNotNull((result as Success).data.toString()) {
            println("Error occurred")
        }
    }*/
    companion object {
        private const val VALID_EMAIL = "dev.aditmodhvadia@gmail.com"
        private const val VALID_PASSWORD = "12345678"
        private const val INVALID_PASSWORD = "1234567890"
    }
}
