package com.fazemeright.myinventorytracker.itemdetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fazemeright.myinventorytracker.database.InventoryDatabase
import com.fazemeright.myinventorytracker.database.InventoryItem
import com.fazemeright.myinventorytracker.database.InventoryItemDao
import kotlinx.coroutines.*

class ItemDetailViewModel(
    val database: InventoryDatabase,
    application: Application,
    itemInBag: InventoryItemDao.ItemInBag
) : AndroidViewModel(application) {

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()

    val item = MutableLiveData<InventoryItemDao.ItemInBag>()

    val navigateBackToItemList = MutableLiveData<Boolean>()

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

    init {
        uiScope.launch {
            item.value = getItemInBagFromId(itemInBag.itemId)
        }
    }

    private suspend fun getItemInBagFromId(itemId: Long): InventoryItemDao.ItemInBag? {
        return withContext(Dispatchers.IO) {
            database.inventoryItemDao.getItemInBagFromId(itemId)
        }
    }

    fun onUpdateClicked(
        itemName: String,
        bagName: String,
        itemDesc: String,
        itemQuantity: String
    ) {
        uiScope.launch {
            //            val updateItem =
//                item.value?.itemInBag?.let { InventoryItem(it, itemName, bagName, itemDesc, itemQuantity.toInt()) }
//            updateItem(updateItem)
            navigateBackToItemList()
        }
    }

    private suspend fun updateItem(item: InventoryItem?) {
        withContext(Dispatchers.IO) {
            item?.let { database.inventoryItemDao.update(it) }
        }
    }

    private fun navigateBackToItemList() {
        Log.d("AddItemViewModel", "Clicked Fab")
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