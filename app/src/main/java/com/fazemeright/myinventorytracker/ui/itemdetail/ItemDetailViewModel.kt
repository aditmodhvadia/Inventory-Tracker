package com.fazemeright.myinventorytracker.ui.itemdetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.fazemeright.myinventorytracker.domain.models.ItemWithBag
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import com.fazemeright.myinventorytracker.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val repository: InventoryRepository,
    app: Application
) : BaseViewModel(app) {

    private val _onItemDeleteComplete = MutableLiveData<Boolean>()

    val onItemDeleteComplete: LiveData<Boolean>
        get() = _onItemDeleteComplete

    private val _itemId = MutableLiveData<Int>()
    val item: LiveData<ItemWithBag> = _itemId.switchMap {
        repository.getItemWithBagFromId(it)
    }

    val navigateBackToItemList = MutableLiveData<Boolean>()

    /*fun onUpdateClicked(
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
    }*/

    /*private suspend fun updateItem(item: InventoryItem?) {
        repository.updateInventoryItem(item)
    }*/

    /*private fun navigateBackToItemList() {
        Timber.i("Clicked Fab")
        navigateBackToItemList.value = true
    }*/

    fun onNavigationToItemListFinished() {
        navigateBackToItemList.value = false
    }

    fun deleteItem() {
        viewModelScope.launch {
            item.value?.let {
                repository.deleteInventoryItem(it.item)
            }

            _onItemDeleteComplete.value = true
        }
    }

    fun onItemDeleteFinished() {
        _onItemDeleteComplete.value = false
    }

    fun setNavArguments(navArgs: ItemDetailFragmentArgs) {
        _itemId.value = navArgs.itemId.toInt()
    }

    /*fun updateBagDesc(selectedBagName: String?) {
        uiScope.launch {
            bag.value = selectedBagName?.let {
                getBagFromName(it)
            }
        }
    }*/
}
