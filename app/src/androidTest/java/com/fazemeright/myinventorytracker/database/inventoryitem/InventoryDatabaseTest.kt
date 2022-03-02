package com.fazemeright.myinventorytracker.database.inventoryitem

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fazemeright.inventorytracker.database.InventoryDatabase
import com.fazemeright.inventorytracker.database.dao.BagItemDao
import com.fazemeright.inventorytracker.database.dao.InventoryItemDao
import com.fazemeright.myinventorytracker.domain.mappers.entity.BagItemEntityMapper
import com.fazemeright.myinventorytracker.domain.mappers.entity.InventoryItemEntityMapper
import com.fazemeright.myinventorytracker.domain.models.InventoryItem
import com.fazemeright.myinventorytracker.utils.TestUtils
import junit.framework.AssertionFailedError
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
// @HiltAndroidTest
class InventoryDatabaseTest {
    private lateinit var db: InventoryDatabase
    private lateinit var inventoryItemDao: InventoryItemDao
    private lateinit var bagItemDao: BagItemDao

    @Before
    fun createDB() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, InventoryDatabase::class.java
        )
            .build()
        inventoryItemDao = db.inventoryItemDao
        bagItemDao = db.bagItemDao
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeInventoryItemFailsWithIncorrectBagItem() {
        val item = InventoryItem(itemId = 1, itemName = "Blazer", bagOwnerId = 0)
        assertTrue(
            assertFails<SQLiteConstraintException> {
                inventoryItemDao.insert(InventoryItemEntityMapper.mapToEntity(item))
            }
        )
    }

    @Test
    @Throws(Exception::class)
    fun writeBagItemAndReadInList() {
        val item = TestUtils.getBagItem(1)
        bagItemDao.insert(BagItemEntityMapper.mapToEntity(item))
        val byName = bagItemDao.findItemsByName(item.bagName)
        assertTrue(byName[0] == BagItemEntityMapper.mapToEntity(item))
        bagItemDao.deleteItem(byName[0])
    }

    @Test
    fun writeBagAndItemAssociation() = runBlocking {
        var bag1 = TestUtils.getBagItem(1)
        var bag2 = TestUtils.getBagItem(2)
        bagItemDao.insert(BagItemEntityMapper.mapToEntity(bag1))
        bagItemDao.insert(BagItemEntityMapper.mapToEntity(bag2))

        bag1 = BagItemEntityMapper.mapFromEntity(bagItemDao.findItemsByName(bag1.bagName)[0])
        bag2 = BagItemEntityMapper.mapFromEntity(bagItemDao.findItemsByName(bag2.bagName)[0])

        var item = TestUtils.getInventoryItem(1, bag2.bagId)
        inventoryItemDao.insert(InventoryItemEntityMapper.mapToEntity(item))
        item =
            InventoryItemEntityMapper.mapFromEntity(inventoryItemDao.findItemsByName("Item 1 inside bag 2")[0])
        assertNotNull(item)

        val itemsInBag2 =
            bagItemDao.getItemsAndBagsInBagWithId(id = bag2.bagId)
        assertEquals(1, itemsInBag2?.items?.size)

        val itemsInBag1 =
            bagItemDao.getItemsAndBagsInBagWithId(id = bag1.bagId)
        assertEquals(0, itemsInBag1?.items?.size)

        val allItemsWithBag = inventoryItemDao.getItemsWithBag()
        assertEquals(1, allItemsWithBag.size)
    }
}

@Throws(InterruptedException::class)
fun <T> getValue(liveData: LiveData<T>): T {
    val data = arrayOfNulls<Any>(1)
    val latch = CountDownLatch(1)
    liveData.observeForever { o ->
        data[0] = o
        latch.countDown()
    }
    latch.await(2, TimeUnit.SECONDS)

    @Suppress("UNCHECKED_CAST")
    return data[0] as T
}

/*
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
//    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

//    afterObserve.invoke()

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        this.removeObserver(observer)
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}
*/

private inline fun <reified T> assertFails(function: () -> Unit): Boolean {
    try {
        function.invoke()
    } catch (e: Exception) {
        if (e.javaClass.name == T::class.java.name) {
            return true
        } else {
            throw AssertionFailedError("Exception type mismatch, expected ${T::class.simpleName}, actual ${e::class.simpleName}")
        }
    }
    return false
}
