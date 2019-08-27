package com.fazemeright.myinventorytracker.itemdetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fazemeright.myinventorytracker.database.InventoryDatabase
import com.fazemeright.myinventorytracker.database.InventoryItemDao

class ItemDetailViewModelFactory(
    private val dataSource: InventoryDatabase,
    private val application: Application,
    private val itemInBag: InventoryItemDao.ItemInBag
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemDetailViewModel::class.java)) {
            return ItemDetailViewModel(dataSource, application, itemInBag) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}