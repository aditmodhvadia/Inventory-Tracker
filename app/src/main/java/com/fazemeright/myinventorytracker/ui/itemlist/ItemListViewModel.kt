package com.fazemeright.myinventorytracker.ui.itemlist

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.database.inventoryitem.ItemWithBag
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import com.fazemeright.myinventorytracker.ui.base.BaseViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * ViewModel for SleepTrackerFragment.
 */
class ItemListViewModel @ViewModelInject constructor(
    private val repository: InventoryRepository,
    @ActivityContext context: Context
) : BaseViewModel(context, repository) {

    private val _searchString = MutableLiveData<String>()

    private val _logoutUser = MutableLiveData<Boolean>()

    val userLoggedOut: LiveData<Boolean>
        get() = _logoutUser

    val navigateToItemDetailActivity = MutableLiveData<ItemWithBag?>()

    val navigateToAddItemActivity = MutableLiveData<Boolean>()

    val deletedItem = MutableLiveData<InventoryItem>()

    init {
        _searchString.value = ""
    }

    val items = _searchString.switchMap {
        if (it.isEmpty()) repository.getItemsWithBagLive()
        else
            liveData {
                emit(
                    repository.searchInventoryItems(it)
                )
            }
    }

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
        repository.getInventoryItemById(itemId)?.let {
            repository.deleteInventoryItem(it)
            return it
        }
        return null
    }

    fun undoDeleteItem(deletedItem: InventoryItem?) {
        viewModelScope.launch {
            insertItemBack(deletedItem)
        }
    }

    private suspend fun insertItemBack(deletedItem: InventoryItem?) {
        deletedItem?.let { repository.insertInventoryItem(it) }
    }

    override fun logoutClicked() {
        super.logoutClicked()
        Timber.d("ItemListViewModel")
        viewModelScope.launch {
            repository.logoutUser()
            _logoutUser.value = true
        }
    }

    fun logoutSuccessful() {
        _logoutUser.value = false
    }
}
