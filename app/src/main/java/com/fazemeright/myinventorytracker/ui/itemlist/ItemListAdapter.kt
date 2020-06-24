package com.fazemeright.myinventorytracker.ui.itemlist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fazemeright.myinventorytracker.database.bag.BagItem
import com.fazemeright.myinventorytracker.database.inventoryitem.ItemWithBag
import com.fazemeright.myinventorytracker.databinding.ListInventoryItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class ItemListAdapter(private val clickListener: ItemListener) :
    ListAdapter<ItemWithBag,
            ItemListAdapter.ViewHolder>(ItemListDiffCallback()) {

    private lateinit var bagsList: List<BagItem>

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    fun updateList(list: List<ItemWithBag>?) {
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

    class ViewHolder private constructor(private val binding: ListInventoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ItemWithBag,
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
        DiffUtil.ItemCallback<ItemWithBag>() {
        override fun areItemsTheSame(
            oldItem: ItemWithBag,
            newItem: ItemWithBag
        ): Boolean {
            return oldItem.item.itemId == newItem.item.itemId
        }

        override fun areContentsTheSame(
            oldItem: ItemWithBag,
            newItem: ItemWithBag
        ): Boolean {
            return oldItem == newItem
        }

    }

    class ItemListener(
        val clickListener: (item: ItemWithBag) -> Unit,
        val deleteClickListener: (itemId: Int) -> Unit
    ) {
        fun onClick(item: ItemWithBag) = clickListener(item)
        fun onDeleteClick(item: ItemWithBag) =
            deleteClickListener(item.item.itemId)
    }
}
