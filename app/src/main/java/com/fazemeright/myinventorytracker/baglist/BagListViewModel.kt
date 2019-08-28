package com.fazemeright.myinventorytracker.baglist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fazemeright.myinventorytracker.database.BagItem
import com.fazemeright.myinventorytracker.database.InventoryDatabase
import com.fazemeright.myinventorytracker.database.InventoryItem
import kotlinx.coroutines.*

/**
 * ViewModel for BagListActivity.
 */
class BagListViewModel(
    val database: InventoryDatabase,
    application: Application
) : AndroidViewModel(application) {

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
     * a [BagListViewModel] update the UI after performing some processing.
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val searchItems: LiveData<List<BagItem>>
        get() = _searchItems

    private val _searchItems = MutableLiveData<List<BagItem>>()

    val bags = database.bagItemDao.getAllBags()

    val navigateToBagDetailActivity = MutableLiveData<BagItem>()

    val navigateToAddBagActivity = MutableLiveData<Boolean>()

//    val deletedItem = MutableLiveData<InventoryItem>()

    init {
        onSearchClicked("")
    }

    fun onSearchClicked(searchText: String) {
        uiScope.launch {
            fetchSearchResults(searchText)
        }
    }

    private suspend fun fetchSearchResults(searchText: String) {
        withContext(Dispatchers.IO) {
            updateItems(database.inventoryItemDao.getSearchBags("%$searchText%"))
        }
    }

    private suspend fun updateItems(newItems: List<BagItem>) {
        withContext(Dispatchers.Main) {
            _searchItems.value = newItems
        }
    }

    fun addBagClicked() {
        navigateToAddBagActivity.value = true
    }

    fun onNavigationToAddBagFinished() {
        navigateToAddBagActivity.value = false
    }

    fun onBagClicked(item: BagItem) {
        navigateToBagDetailActivity.value = item
    }

    fun onNavigationToItemDetailFinished() {
        navigateToBagDetailActivity.value = null
    }

    //    TODO: Update all delete, undo and insert functions with BagItem
    fun onDeleteItemClicked(itemId: Long) {
        uiScope.launch {
            //            deletedItem.value = deleteItem(itemId)
        }
    }

    /*private suspend fun deleteItem(itemId: Long): InventoryItem? {
        return withContext(Dispatchers.IO) {
            val deleteItem = database.inventoryItemDao.get(itemId)
            deleteItem?.let {
                database.inventoryItemDao.deleteItem(it)
            }
            deleteItem
        }
    }*/

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
