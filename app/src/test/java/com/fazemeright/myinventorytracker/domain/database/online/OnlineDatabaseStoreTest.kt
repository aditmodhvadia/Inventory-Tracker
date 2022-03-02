package com.fazemeright.myinventorytracker.domain.database.online

import com.fazemeright.myinventorytracker.domain.database.online.mock.MockOnlineDatabaseStoreImpl
import com.fazemeright.myinventorytracker.domain.models.Result
import com.fazemeright.myinventorytracker.utils.TestUtils
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

class OnlineDatabaseStoreTest : TestCase() {
    private lateinit var onlineDataStoreImpl: OnlineDatabaseStore

    @Before
    override fun setUp() {
        onlineDataStoreImpl = MockOnlineDatabaseStoreImpl()
    }

    @After
    override fun tearDown() {

    }

    fun testGetAllBags() = runBlocking {
        when (val allBagsResult = onlineDataStoreImpl.getAllBags()) {
            is Result.Error -> fail("Get all bags returned error result")
            is Result.Success -> assertEquals(true, allBagsResult.data.isEmpty())
        }
        (1..5).forEach {
            onlineDataStoreImpl.storeBag(TestUtils.getBagItem(it))
        }
        when (val allBagsResult = onlineDataStoreImpl.getAllBags()) {
            is Result.Error -> fail("Get all bags returned error result")
            is Result.Success -> {
                assertEquals(true, allBagsResult.data.isNotEmpty())
                assertEquals(5, allBagsResult.data.size)
                assertEquals(5, allBagsResult.data.mapNotNull { it.onlineId }.toSet().size)
            }
        }
    }

    fun testGetAllInventoryItems() = runBlocking {
        when (val allInventoryItemsResult = onlineDataStoreImpl.getAllInventoryItems()) {
            is Result.Error -> fail("Get all inventory items returned error result")
            is Result.Success -> assertEquals(true, allInventoryItemsResult.data.isEmpty())
        }
        (1..5).forEach {
            onlineDataStoreImpl.storeInventoryItem(TestUtils.getInventoryItem(it, 1))
        }
        when (val allInventoryItemsResult = onlineDataStoreImpl.getAllInventoryItems()) {
            is Result.Error -> fail("Get all inventory items returned error result")
            is Result.Success -> {
                assertEquals(true, allInventoryItemsResult.data.isNotEmpty())
                assertEquals(5, allInventoryItemsResult.data.size)
                assertEquals(5, allInventoryItemsResult.data.mapNotNull { it.onlineId }.toSet().size)
            }
        }
    }

    fun testStoreInventoryItem() {}

    fun testStoreBag() {}

    fun testDeleteInventoryItem() {}

    fun testDeleteBag() {}

    fun testBatchWriteBags() {}

    fun testBatchWriteInventoryItems() {}
}