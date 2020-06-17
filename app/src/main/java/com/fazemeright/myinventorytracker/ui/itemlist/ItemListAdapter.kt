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

package com.fazemeright.myinventorytracker.ui.itemlist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fazemeright.myinventorytracker.database.bag.BagItem
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItemDao
import com.fazemeright.myinventorytracker.databinding.ListInventoryItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class ItemListAdapter(private val clickListener: ItemListener) :
    ListAdapter<InventoryItemDao.ItemInBag,
            ItemListAdapter.ViewHolder>(ItemListDiffCallback()) {

    private lateinit var bagsList: List<BagItem>

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    fun updateList(list: List<InventoryItemDao.ItemInBag>?) {
        Log.d("##DebugData", list.toString())
        submitList(list)
    }

    fun updateBagList(updatedList: List<BagItem>) {
        this.bagsList = updatedList
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: ListInventoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            item: InventoryItemDao.ItemInBag,
            clickListener: ItemListener
        ) {
            binding.item = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListInventoryItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class ItemListDiffCallback :
        DiffUtil.ItemCallback<InventoryItemDao.ItemInBag>() {
        override fun areItemsTheSame(
            oldItem: InventoryItemDao.ItemInBag,
            newItem: InventoryItemDao.ItemInBag
        ): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(
            oldItem: InventoryItemDao.ItemInBag,
            newItem: InventoryItemDao.ItemInBag
        ): Boolean {
            return oldItem == newItem
        }

    }

    class ItemListener(
        val clickListener: (item: InventoryItemDao.ItemInBag) -> Unit,
        val deleteClickListener: (itemId: Long) -> Unit
    ) {
        fun onClick(item: InventoryItemDao.ItemInBag) = clickListener(item)
        fun onDeleteClick(item: InventoryItemDao.ItemInBag) = deleteClickListener(item.itemId)
    }
}
