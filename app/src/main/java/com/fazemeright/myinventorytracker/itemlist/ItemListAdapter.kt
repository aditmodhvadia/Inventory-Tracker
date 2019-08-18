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

package com.fazemeright.myinventorytracker.itemlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fazemeright.myinventorytracker.database.BagItem
import com.fazemeright.myinventorytracker.database.InventoryItem
import com.fazemeright.myinventorytracker.databinding.ListInventoryItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class ItemListAdapter(private val clickListener: ItemListener) : ListAdapter<InventoryItem,
        ItemListAdapter.ViewHolder>(ItemListDiffCallback()) {

    private lateinit var bagsList: List<BagItem>

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    fun updateList(list: List<InventoryItem>?) {
        submitList(list)
    }

    fun updateBagList(updatedList: List<BagItem>) {
        this.bagsList = updatedList
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val bag = getBagFromId(item.bagId)
        holder.bind(item, clickListener, bag)
    }

    private fun getBagFromId(bagId: Long): BagItem {
        var bag: BagItem? = null
        bagsList.forEach { bagItem ->
            if (bagItem.bagId == bagId) {
                bag = bagItem
            }
        }
        return bag!!
    }


    class ViewHolder private constructor(val binding: ListInventoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            item: InventoryItem,
            clickListener: ItemListener,
            bag: BagItem
        ) {
            binding.item = item
            binding.bag = bag
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
        DiffUtil.ItemCallback<InventoryItem>() {
        override fun areItemsTheSame(oldItem: InventoryItem, newItem: InventoryItem): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(oldItem: InventoryItem, newItem: InventoryItem): Boolean {
            return oldItem == newItem
        }

    }

    class ItemListener(val clickListener: (sleepId: Long) -> Unit, val deleteClickListener: (sleepId: Long) -> Unit) {
        fun onClick(item: InventoryItem) = clickListener(item.itemId)
        fun onDeleteClick(item: InventoryItem) = deleteClickListener(item.itemId)
    }
}
