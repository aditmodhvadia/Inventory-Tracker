package com.fazemeright.myinventorytracker.ui.itemdetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fazemeright.myinventorytracker.database.InventoryDatabase
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItemDao
import com.fazemeright.myinventorytracker.database.inventoryitem.ItemWithBag

class ItemDetailViewModelFactory(
    private val dataSource: InventoryDatabase,
    private val itemWithBag: ItemWithBag
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemDetailViewModel::class.java)) {
            return ItemDetailViewModel(dataSource, itemWithBag) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}