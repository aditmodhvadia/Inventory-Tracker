package com.fazemeright.myinventorytracker.itemdetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fazemeright.myinventorytracker.database.BagItem
import com.fazemeright.myinventorytracker.database.InventoryDatabase
import com.fazemeright.myinventorytracker.database.InventoryItem
import kotlinx.coroutines.*

class ItemDetailViewModel(
    val database: InventoryDatabase,
    application: Application,
    itemId: Long
) : AndroidViewModel(application) {

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()

    val item = MutableLiveData<InventoryItem>()

    val bag = MutableLiveData<BagItem>()

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

    val bagNames = database.bagItemDao.getAllBagNames()


    init {
        uiScope.launch {
            item.value = getItemFromId(itemId)
            bag.value = getBagFromId(item.value?.bagId)
        }
    }

    private suspend fun getBagFromId(bagId: Long?): BagItem? {
        return withContext(Dispatchers.IO) {
            bagId?.let { database.bagItemDao.get(it) }
        }
    }

    private suspend fun getItemFromId(itemId: Long): InventoryItem? {
        return withContext(Dispatchers.IO) {
            database.inventoryItemDao.get(itemId)
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
//                item.value?.itemId?.let { InventoryItem(it, itemName, bagName, itemDesc, itemQuantity.toInt()) }
//            upDateItem(updateItem)
            navigateBackToItemList()
        }
    }

    private suspend fun upDateItem(item: InventoryItem?) {
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

    fun updateBagDesc(selectedBagName: String?) {
        uiScope.launch {
            bag.value = selectedBagName?.let {
                getBagFromName(it)
            }
        }
    }

    private suspend fun getBagFromName(bagName: String): BagItem? {
        return withContext(Dispatchers.IO) {
            getBagFromId(database.bagItemDao.getBagIdWithName(bagName))
        }
    }

}