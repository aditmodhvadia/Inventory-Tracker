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

package com.fazemeright.myinventorytracker.ui.addbag

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazemeright.myinventorytracker.database.bag.BagItem
import com.fazemeright.myinventorytracker.database.bag.BagItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * ViewModel for SleepTrackerFragment.
 */
class AddBagViewModel @ViewModelInject constructor(
    private val bagItemDao: BagItemDao
) : ViewModel() {

    val navigateBackToItemList = MutableLiveData<Boolean>()

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            bagItemDao.clear()
        }
    }

    fun onAddClicked(
        itemName: String,
        bagName: String,
        itemDesc: String,
        itemQuantity: String
    ) {
        viewModelScope.launch {
            Timber.d(itemName)
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
        viewModelScope.launch {
            val newBag = BagItem(
                0,
                bagName,
                bagColor,
                bagDesc
            )
            insertNewBag(newBag)
            navigateBackToItemList()
        }
    }

    private suspend fun insertNewBag(newBag: BagItem) {
        withContext(Dispatchers.IO) {
            bagItemDao.insert(newBag)
            Timber.d(bagItemDao.getAllBags().value?.size.toString())
        }
    }
}
