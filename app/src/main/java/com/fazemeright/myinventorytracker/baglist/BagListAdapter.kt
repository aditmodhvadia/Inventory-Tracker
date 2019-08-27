package com.fazemeright.myinventorytracker.baglist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fazemeright.myinventorytracker.database.BagItem
import com.fazemeright.myinventorytracker.database.InventoryItemDao
import com.fazemeright.myinventorytracker.databinding.ListBagItemBinding
import com.fazemeright.myinventorytracker.databinding.ListInventoryItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class BagListAdapter(private val clickListener: BagListener) :
    ListAdapter<InventoryItemDao.ItemInBag,
            BagListAdapter.ViewHolder>(ItemListDiffCallback()) {

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

    class ViewHolder private constructor(val binding: ListBagItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            item: InventoryItemDao.ItemInBag,
            clickListener: BagListener
        ) {
            binding.item = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListBagItemBinding.inflate(layoutInflater, parent, false)
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

    class BagListener(
        val clickListener: (item: InventoryItemDao.ItemInBag) -> Unit,
        val deleteClickListener: (itemId: Long) -> Unit
    ) {
        fun onClick(item: InventoryItemDao.ItemInBag) = clickListener(item)
        fun onDeleteClick(item: InventoryItemDao.ItemInBag) = deleteClickListener(item.itemId)
    }
}
