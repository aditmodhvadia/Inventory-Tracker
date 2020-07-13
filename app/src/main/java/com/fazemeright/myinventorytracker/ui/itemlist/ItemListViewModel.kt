package com.fazemeright.myinventorytracker.ui.itemlist

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.fazemeright.myinventorytracker.database.bag.BagItemDao
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItemDao
import com.fazemeright.myinventorytracker.database.inventoryitem.ItemWithBag
import com.fazemeright.myinventorytracker.ui.base.BaseViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for SleepTrackerFragment.
 */
class ItemListViewModel @ViewModelInject constructor(
    bagItemDao: BagItemDao,
    private val inventoryItemDao: InventoryItemDao,
    @ActivityContext context: Context
) : BaseViewModel(context) {

    private val _searchString = MutableLiveData<String>()

    val items = _searchString.switchMap {
        if (it.isEmpty()) inventoryItemDao.getItemsWithBagLive()
        else
            liveData {
                emit(
                    withContext(Dispatchers.IO + Job()) {
                        inventoryItemDao.searchItems(it)
                    }
                )
            }
    }

    init {
        _searchString.value = ""
    }

    val bags = bagItemDao.getAllBags()

    val navigateToItemDetailActivity = MutableLiveData<ItemWithBag>()

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

    fun onItemClicked(item: ItemWithBag) {
        navigateToItemDetailActivity.value = item
    }

    fun onNavigationToItemDetailFinished() {
        navigateToItemDetailActivity.value = null
    }

    fun onDeleteItemClicked(itemId: Int) {
        viewModelScope.launch {
            deletedItem.value = deleteItem(itemId)
        }
    }

    private suspend fun deleteItem(itemId: Int): InventoryItem? {
        return withContext(Dispatchers.IO) {
            val deleteItem = inventoryItemDao.getById(itemId)
            deleteItem?.let {
                inventoryItemDao.deleteItem(it)
            }
            deleteItem
        }
    }

    fun undoDeleteItem(deletedItem: InventoryItem?) {
        viewModelScope.launch {
            insertItemBack(deletedItem)
        }
    }

    private suspend fun insertItemBack(deletedItem: InventoryItem?) {
        withContext(Dispatchers.IO) {
            deletedItem?.let { inventoryItemDao.insert(it) }
        }
    }
}
