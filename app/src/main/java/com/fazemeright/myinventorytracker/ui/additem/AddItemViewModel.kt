package com.fazemeright.myinventorytracker.ui.additem

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import com.fazemeright.myinventorytracker.ui.base.BaseViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.launch
import timber.log.Timber

class AddItemViewModel @ViewModelInject constructor(
    private val repository: InventoryRepository,
    @ActivityContext private val context: Context
) : BaseViewModel(context, repository) {

    private val newInventoryItem by lazy { InventoryItem() }

    val bagNames = repository.getAllBagNames()

    val itemName: String = ""
    val bagName: String = "Black AT+"
    val desc: String = ""
    val itemQuantity: Int = 1

    val navigateBackToItemList = MutableLiveData<Boolean>()

    private suspend fun clear() {
        repository.clearInventoryItems()
    }

    fun onAddClicked(
        newItemName: String,
        bagName: String,
        newItemDesc: String,
        newItemQuantity: String
    ) {
        viewModelScope.launch {
            newInventoryItem.apply {
                itemName = newItemName
                itemDesc = newItemDesc
                itemQuantity = newItemQuantity.toInt()
                bagOwnerId = getBagId(bagName)
            }

            Timber.d("$newInventoryItem")
            insert(newInventoryItem)
            navigateBackToItemList()
        }
    }

    private suspend fun getBagId(bagName: String): Int {
        return repository.getBagIdWithName(bagName)
    }

    private suspend fun insert(newItem: InventoryItem) {
        repository.insertInventoryItem(newItem)
    }

    private fun navigateBackToItemList() {
        navigateBackToItemList.value = true
    }

    fun onNavigationToAddItemFinished() {
        navigateBackToItemList.value = false
    }
}
