package com.fazemeright.myinventorytracker.ui.additem

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fazemeright.myinventorytracker.database.InventoryDatabase
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class AddItemViewModel(
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

    val bags = database.bagItemDao.getAllBags()

    val bagNames = database.bagItemDao.getAllBagNames()

    val itemName: String = ""
    val bagName: String = "Black AT+"
    val desc: String = ""
    val itemQuantity: Int = 1

    val navigateBackToItemList = MutableLiveData<Boolean>()

    init {

    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.inventoryItemDao.clear()
        }
    }

    fun onAddClicked(
        itemName: String,
        bagName: String,
        itemDesc: String,
        itemQuantity: String
    ) {
        uiScope.launch {
            Log.d("##DebugData", itemName)
            val newItem =
                InventoryItem(
                    0,
                    itemName,
                    itemDesc,
                    itemQuantity.toInt(),
                    getBagId(bagName)
                )
            Log.d("##DebugData", newItem.toString())
            insert(newItem)
            navigateBackToItemList()
        }
    }

    private suspend fun getBagId(bagName: String): Int {
        return withContext(Dispatchers.IO) {
            database.bagItemDao.getBagIdWithName(bagName)
        }
    }

    private suspend fun insert(night: InventoryItem) {
        withContext(Dispatchers.IO) {
            database.inventoryItemDao.insert(night)
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
