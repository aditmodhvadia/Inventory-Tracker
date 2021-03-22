package com.fazemeright.myinventorytracker.ui.additem

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fazemeright.myinventorytracker.domain.models.InventoryItem
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import com.fazemeright.myinventorytracker.repository.Repository
import com.fazemeright.myinventorytracker.ui.base.BaseViewModel
import com.fazemeright.myinventorytracker.usecase.ClearAllInventoryItemsUseCase
import com.fazemeright.myinventorytracker.usecase.GetAllBagNamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    @ActivityContext private val context: Context,
    getAllBagNamesUseCase: GetAllBagNamesUseCase,
    private val clearAllInventoryItemsUseCase: ClearAllInventoryItemsUseCase,
    private val repository: InventoryRepository
) : BaseViewModel(context) {

    private val newInventoryItem by lazy { InventoryItem() }

    val bagNames = getAllBagNamesUseCase()

    val itemName: String = ""
    val bagName: String = "Black AT+"
    val desc: String = ""
    val itemQuantity: Int = 1

    val navigateBackToItemList = MutableLiveData<Boolean>()

    private suspend fun clear() {
        clearAllInventoryItemsUseCase()
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
