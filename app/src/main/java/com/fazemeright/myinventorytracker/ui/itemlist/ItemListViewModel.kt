package com.fazemeright.myinventorytracker.ui.itemlist

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.fazemeright.myinventorytracker.data.InventoryRepository
import com.fazemeright.myinventorytracker.database.bag.BagItemDao
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.database.inventoryitem.ItemWithBag
import com.fazemeright.myinventorytracker.ui.base.BaseViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.launch

/**
 * ViewModel for SleepTrackerFragment.
 */
class ItemListViewModel @ViewModelInject constructor(
    bagItemDao: BagItemDao,
    private val repository: InventoryRepository,
    @ActivityContext context: Context
) : BaseViewModel(context) {

    private val _searchString = MutableLiveData<String>()

    val items = _searchString.switchMap {
        if (it.isEmpty()) repository.getItemsWithBagLive()
        else
            liveData {
                emit(
//                    withContext(Dispatchers.IO + Job()) {
                    repository.searchInventoryItems(it)
//                    }
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
        val deleteItem = repository.getInventoryItemById(itemId)
        deleteItem?.let {
            repository.deleteInventoryItem(it)
        }
        return deleteItem
    }

    fun undoDeleteItem(deletedItem: InventoryItem?) {
        viewModelScope.launch {
            insertItemBack(deletedItem)
        }
    }

    private suspend fun insertItemBack(deletedItem: InventoryItem?) {
        deletedItem?.let { repository.insertInventoryItem(it) }
    }
}
