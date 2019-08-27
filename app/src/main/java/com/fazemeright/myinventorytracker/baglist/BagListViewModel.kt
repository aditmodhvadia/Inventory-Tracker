/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fazemeright.myinventorytracker.baglist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fazemeright.myinventorytracker.database.InventoryDatabase
import com.fazemeright.myinventorytracker.database.InventoryItem
import com.fazemeright.myinventorytracker.database.InventoryItemDao
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
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
     * a [ViewModel] update the UI after performing some processing.
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val searchItems: LiveData<List<InventoryItemDao.ItemInBag>>
        get() = _searchItems

    private val _searchItems = MutableLiveData<List<InventoryItemDao.ItemInBag>>()

//    val items = database.inventoryItemDao.getAllItems()

    val items = database.inventoryItemDao.getAllItemsWithBag()

    val bags = database.bagItemDao.getAllBags()

    val navigateToItemDetailActivity = MutableLiveData<InventoryItemDao.ItemInBag>()

    val navigateToAddItemActivity = MutableLiveData<Boolean>()

    val deletedItem = MutableLiveData<InventoryItem>()

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
            updateItems(database.inventoryItemDao.getSearchItems("%$searchText%"))
        }
    }

    private suspend fun updateItems(newItems: List<InventoryItemDao.ItemInBag>) {
        withContext(Dispatchers.Main) {
            _searchItems.value = newItems
        }
    }

    fun addItemClicked() {
        navigateToAddItemActivity.value = true
    }

    fun onNavigationToAddItemFinished() {
        navigateToAddItemActivity.value = false
    }

    fun onItemClicked(item: InventoryItemDao.ItemInBag) {
        navigateToItemDetailActivity.value = item
    }

    fun onNavigationToItemDetailFinished() {
        navigateToItemDetailActivity.value = null
    }

    fun onDeleteItemClicked(itemId: Long) {
        uiScope.launch {
            deletedItem.value = deleteItem(itemId)
        }
    }

    private suspend fun deleteItem(itemId: Long): InventoryItem? {
        return withContext(Dispatchers.IO) {
            val deleteItem = database.inventoryItemDao.get(itemId)
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
