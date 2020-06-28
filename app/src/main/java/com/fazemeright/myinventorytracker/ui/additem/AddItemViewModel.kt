package com.fazemeright.myinventorytracker.ui.additem

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazemeright.myinventorytracker.database.bag.BagItemDao
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for SleepTrackerFragment.
 */
class AddItemViewModel @ViewModelInject constructor(
    private val bagItemDao: BagItemDao,
    private val inventoryItemDao: InventoryItemDao
) : ViewModel() {

    private val newInventoryItem by lazy { InventoryItem() }
    val bags = bagItemDao.getAllBags()

    val bagNames = bagItemDao.getAllBagNames()

    val itemName: String = ""
    val bagName: String = "Black AT+"
    val desc: String = ""
    val itemQuantity: Int = 1

    val navigateBackToItemList = MutableLiveData<Boolean>()

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            inventoryItemDao.clear()
        }
    }

    fun onAddClicked(
        itemName: String,
        bagName: String,
        itemDesc: String,
        itemQuantity: String
    ) {
        viewModelScope.launch {
            Log.d("##DebugData", itemName)
            newInventoryItem.itemName = itemName
            newInventoryItem.itemDesc = itemDesc
            newInventoryItem.itemQuantity = itemQuantity.toInt()
            newInventoryItem.bagOwnerId = getBagId(bagName)

            Log.d("##DebugData", newInventoryItem.toString())
            insert(newInventoryItem)
            navigateBackToItemList()
        }
    }

    private suspend fun getBagId(bagName: String): Int {
        return withContext(Dispatchers.IO) {
            bagItemDao.getBagIdWithName(bagName)
        }
    }

    private suspend fun insert(newItem: InventoryItem) {
        withContext(Dispatchers.IO) {
            inventoryItemDao.insert(newItem)
        }
    }

    private fun navigateBackToItemList() {
        Log.d("AddItemViewModel", "Clicked Fab")
        navigateBackToItemList.value = true
    }

    fun onNavigationToAddItemFinished() {
        navigateBackToItemList.value = false
    }
}
