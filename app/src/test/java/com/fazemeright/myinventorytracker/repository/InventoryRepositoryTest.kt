package com.fazemeright.myinventorytracker.repository

import com.fazemeright.inventorytracker.database.dao.BagItemDao
import com.fazemeright.inventorytracker.database.dao.InventoryItemDao
import com.fazemeright.myinventorytracker.domain.authentication.UserAuthentication
import com.fazemeright.myinventorytracker.domain.database.online.OnlineDatabaseStore
import com.fazemeright.myinventorytracker.domain.mappers.entity.BagItemEntityMapper.mapDomainToEntity
import com.fazemeright.myinventorytracker.domain.mappers.entity.InventoryItemEntityMapper.mapDomainToEntity
import com.fazemeright.myinventorytracker.domain.models.BagItem
import com.fazemeright.myinventorytracker.domain.models.InventoryItem
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class InventoryRepositoryTest {

    private val bagItemDao: BagItemDao = mockk(relaxed = true)
    private val inventoryItemDao: InventoryItemDao = mockk(relaxed = true)
    private val userAuthentication: UserAuthentication = mockk(relaxed = true)
    private val onlineDatabaseStore: OnlineDatabaseStore = mockk(relaxed = true)

    private val inventoryRepository: InventoryRepository = InventoryRepository(
        bagItemDao,
        inventoryItemDao,
        userAuthentication,
        onlineDatabaseStore,
    )
    private val email = "test@test.com"
    private val password = "this_is_a_password"

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        // 1
        Dispatchers.setMain(testDispatcher)

    }

    @Test
    fun `should call isUserSignedIn once`() {
        inventoryRepository.isUserSignedIn()

        verify(exactly = 1) { userAuthentication.isUserSignedIn() }
    }

    @Test
    fun `should call clearBagItems once`() = runBlocking {
        inventoryRepository.clearBagItems()

        verify(exactly = 1) { bagItemDao.clear() }
    }

    @Test
    fun `should call addBag once`() = runBlocking {
        val bag = BagItem()
        inventoryRepository.addBag(bag)

        verify(exactly = 1) { bagItemDao.insert(bag.mapDomainToEntity()) }
        verify(exactly = 0) { bagItemDao.get(bag.bagId.toLong()) }
    }

    @Test
    fun `should call getAllBags once`() = runBlocking {
        inventoryRepository.getAllBags()

        verify(exactly = 1) { bagItemDao.getAllBags() }
    }

    @Test
    fun `should call getAllBagNames once`() = runBlocking {
        inventoryRepository.getAllBagNames()

        verify(exactly = 1) { bagItemDao.getAllBagNames() }
    }

    @Test
    fun `should call clear once for clearInventoryItems`() = runBlocking {
        inventoryRepository.clearInventoryItems()

        verify(exactly = 1) { inventoryItemDao.clear() }
    }

    @Test
    fun `should call getBagIdWithName once`() = runBlocking {
        val bagName = "bag_name"
        inventoryRepository.getBagIdWithName(bagName)

        verify(exactly = 1) { bagItemDao.getBagIdWithName(bagName) }
    }

    @Test
    fun `should call insertInventoryItem once`() = runBlocking {
        val inventoryItem = InventoryItem()
        inventoryRepository.insertInventoryItem(inventoryItem)

        verify(exactly = 1) { inventoryItemDao.insert(inventoryItem.mapDomainToEntity()) }
        verify(exactly = 0) { inventoryItemDao.getById(inventoryItem.itemId) }
    }

    @Test
    fun `should call getItemWithBagFromId once`() = runBlocking {
        val itemId = 0
        inventoryRepository.getItemWithBagFromId(itemId)

        verify(exactly = 1) { inventoryItemDao.getItemWithBagFromId(itemId) }
    }

    @Test
    fun `should not call updateInventoryItem`() = runBlocking {
        val inventoryItem = InventoryItem()

        inventoryRepository.updateInventoryItem(inventoryItem)

        verify(exactly = 1) { inventoryItemDao.update(inventoryItem.mapDomainToEntity()) }
    }

    @Test
    fun `should not call updateInventoryItem when item is null`() = runBlocking {

        inventoryRepository.updateInventoryItem(null)

        verify(exactly = 0) { inventoryItemDao.update(InventoryItem().mapDomainToEntity()) }
    }

    @Test
    fun `should  logout user and clear inventory and bag items from local`() = runBlocking {

        inventoryRepository.logoutUser()

        verify(exactly = 1) { userAuthentication.logout() }
        verify(exactly = 1) { inventoryItemDao.clear() }
        verify(exactly = 1) { bagItemDao.clear() }
    }

    /*@ExperimentalCoroutinesApi
    @Test
    fun `should call registerWithEmailPassword once`() = runBlockingTest {
        // given

        // when
        inventoryRepository.registerWithEmailPassword(email, password)

        // then
        coVerify(exactly = 1) {
            userAuthentication.register(
                email,
                password
            )
        }
    }*/
}
