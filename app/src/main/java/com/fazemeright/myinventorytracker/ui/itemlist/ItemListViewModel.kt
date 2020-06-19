package com.fazemeright.myinventorytracker.ui.itemlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.fazemeright.myinventorytracker.database.InventoryDatabase
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItemDao
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class ItemListViewModel(
    val database: InventoryDatabase
) : ViewModel() {

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _searchString = MutableLiveData<String>()

    val items = _searchString.switchMap {
        liveData {
            emit(
                withContext(Dispatchers.IO + viewModelJob) {
                    database.inventoryItemDao.searchItems(it)
                }
            )
        }
    }

    init {
        _searchString.value = ""
    }

    val bags = database.bagItemDao.getAllBags()

    val navigateToItemDetailActivity = MutableLiveData<InventoryItemDao.ItemWithBag>()

    val navigateToAddItemActivity = MutableLiveData<Boolean>()

    val deletedItem = MutableLiveData<InventoryItem>()

    fun onSearchClicked(searchText: String) {
        _searchString.value = searchText
    }

    fun addItemClicked() {
        navigateToAddItemActivity.value = true
    }

    fun onNavigationToAddItemFinished() {
        navigateToAddItemActivity.value = false
    }

    fun onItemClicked(item: InventoryItemDao.ItemWithBag) {
        navigateToItemDetailActivity.value = item
    }

    fun onNavigationToItemDetailFinished() {
        navigateToItemDetailActivity.value = null
    }

    fun onDeleteItemClicked(itemId: Int) {
        uiScope.launch {
            deletedItem.value = deleteItem(itemId)
        }
    }

    private suspend fun deleteItem(itemId: Int): InventoryItem? {
        return withContext(Dispatchers.IO) {
            val deleteItem = database.inventoryItemDao.getById(itemId)
            deleteItem?.let {
                database.inventoryItemDao.deleteItem(it)
            }
            deleteItem
        }
    }

    fun undoDeleteItem(deletedItem: InventoryItem?) {
        uiScope.launch {
            insertItemBack(deletedItem)
        }
    }

    private suspend fun insertItemBack(deletedItem: InventoryItem?) {
        withContext(Dispatchers.IO) {
            deletedItem?.let { database.inventoryItemDao.insert(it) }
        }
    }
}
