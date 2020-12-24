package com.fazemeright.myinventorytracker.ui.itemlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fazemeright.myinventorytracker.domain.models.ItemWithBag
import com.fazemeright.myinventorytracker.databinding.ListInventoryItemBinding

class ItemListAdapter(private val clickListener: ItemListener) :
    ListAdapter<ItemWithBag,
            ItemListAdapter.ViewHolder>(ItemListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    fun updateList(list: List<ItemWithBag>?) {
        submitList(list)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).also {
            holder.bind(it, clickListener)
        }
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
