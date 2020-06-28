package com.fazemeright.myinventorytracker.ui.itemdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItemDao
import com.fazemeright.myinventorytracker.database.inventoryitem.ItemWithBag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class ItemDetailViewModel @ViewModelInject constructor(
    private val inventoryItemDao: InventoryItemDao,
    itemWithBag: ItemWithBag
) : ViewModel() {

    val item = inventoryItemDao.getItemWithBagFromId(itemWithBag.item.itemId)

    val navigateBackToItemList = MutableLiveData<Boolean>()

    fun onUpdateClicked(
        itemName: String,
        bagName: String,
        itemDesc: String,
        itemQuantity: String
    ) {
        viewModelScope.launch {
            //            val updateItem =
//                item.value?.itemInBag?.let { InventoryItem(it, itemName, bagName, itemDesc, itemQuantity.toInt()) }
//            updateItem(updateItem)
            navigateBackToItemList()
        }
    }

    private suspend fun updateItem(item: InventoryItem?) {
        withContext(Dispatchers.IO) {
            item?.let { inventoryItemDao.update(it) }
        }
    }

    private fun navigateBackToItemList() {
        Timber.i("Clicked Fab")
        navigateBackToItemList.value = true
    }

    fun onNavigationToAddItemFinished() {
        navigateBackToItemList.value = false
    }

    /*fun updateBagDesc(selectedBagName: String?) {
        uiScope.launch {
            bag.value = selectedBagName?.let {
                getBagFromName(it)
            }
        }
    }*/

}