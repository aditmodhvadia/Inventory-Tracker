package com.fazemeright.myinventorytracker.ui.addbag

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fazemeright.myinventorytracker.domain.models.BagItem
import com.fazemeright.myinventorytracker.repository.InventoryRepository
import com.fazemeright.myinventorytracker.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel for SleepTrackerFragment.
 */
@HiltViewModel
class AddBagViewModel @Inject constructor(
    private val repository: InventoryRepository,
    @ActivityContext context: Context
) : BaseViewModel(context) {

    val navigateBackToItemList = MutableLiveData<Boolean>()

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            repository.clearBagItems()
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
                bagName = bagName,
                bagColor = bagColor,
                bagDesc = bagDesc,
            )
            insertNewBag(newBag)
            navigateBackToItemList()
        }
    }

    private suspend fun insertNewBag(newBag: BagItem) {
        repository.addBag(newBag)
    }
}
