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

package com.fazemeright.myinventorytracker.addbag

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fazemeright.myinventorytracker.database.BagItem
import com.fazemeright.myinventorytracker.database.InventoryDatabase
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class AddBagViewModel(
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

    val navigateBackToItemList = MutableLiveData<Boolean>()

    init {

    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.bagItemDao.clear()
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
//            val newItem = InventoryItem(0, itemName, bagName, itemDesc, itemQuantity.toInt())
//            Log.d("##DebugData", newItem.toString())
//            insert(newItem)
            navigateBackToItemList()
        }
    }


    private fun navigateBackToItemList() {
        navigateBackToItemList.value = true
    }

    fun onNavigationToAddItemFinished() {
        navigateBackToItemList.value = false
    }

    fun onAddBagClicked(bagName: String, bagColor: Int, bagDesc: String) {
        uiScope.launch {
            val newBag = BagItem(0, bagName, bagColor, bagDesc)
            insertNewBag(newBag)
            navigateBackToItemList()
        }
    }

    private suspend fun insertNewBag(newBag: BagItem) {
        withContext(Dispatchers.IO) {
            database.bagItemDao.insert(newBag)
            Log.d("##DebugData", database.bagItemDao.getAllBags().value?.size.toString())
        }
    }
}
