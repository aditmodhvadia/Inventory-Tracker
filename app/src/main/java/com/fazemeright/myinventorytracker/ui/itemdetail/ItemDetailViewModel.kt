package com.fazemeright.myinventorytracker.ui.itemdetail

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItem
import com.fazemeright.myinventorytracker.database.inventoryitem.ItemWithBag
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import com.fazemeright.myinventorytracker.ui.base.BaseViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.launch
import timber.log.Timber

class ItemDetailViewModel @ViewModelInject constructor(
    private val repository: InventoryRepository,
    itemWithBag: ItemWithBag,
    @ActivityContext context: Context
) : BaseViewModel(context, repository) {


    private val _onItemDeleteComplete = MutableLiveData<Boolean>()

    val onItemDeleteComplete: LiveData<Boolean>
        get() = _onItemDeleteComplete

    val item = repository.getItemWithBagFromId(itemWithBag.item.itemId)

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
        repository.updateItem(item)
    }

    private fun navigateBackToItemList() {
        Timber.i("Clicked Fab")
        navigateBackToItemList.value = true
    }

    fun onNavigationToItemListFinished() {
        navigateBackToItemList.value = false
    }

    fun deleteItem() {
        viewModelScope.launch {
            repository.deleteInventoryItem(item.value!!.item)

            _onItemDeleteComplete.value = true
        }
    }

    fun onItemDeleteFinished() {
        _onItemDeleteComplete.value = false
    }

    /*fun updateBagDesc(selectedBagName: String?) {
        uiScope.launch {
            bag.value = selectedBagName?.let {
                getBagFromName(it)
            }
        }
    }*/

}