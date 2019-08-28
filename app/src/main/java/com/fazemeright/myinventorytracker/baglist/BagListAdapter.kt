package com.fazemeright.myinventorytracker.baglist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fazemeright.myinventorytracker.database.BagItem
import com.fazemeright.myinventorytracker.databinding.ListBagItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class BagListAdapter(private val clickListener: BagListener) :
    ListAdapter<BagItem,
            BagListAdapter.ViewHolder>(ItemListDiffCallback()) {

    private lateinit var bagsList: List<BagItem>

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    fun updateList(list: List<BagItem>?) {
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
            item: BagItem,
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
        DiffUtil.ItemCallback<BagItem>() {
        override fun areItemsTheSame(
            oldBag: BagItem,
            newBag: BagItem
        ): Boolean {
            return oldBag.bagId == newBag.bagId
        }

        override fun areContentsTheSame(
            oldBag: BagItem,
            newBag: BagItem
        ): Boolean {
            return oldBag == newBag
        }

    }

    class BagListener(
        val clickListener: (bag: BagItem) -> Unit,
        val deleteClickListener: (bagId: Long) -> Unit
    ) {
        fun onClick(bag: BagItem) = clickListener(bag)
        fun onDeleteClick(bag: BagItem) = deleteClickListener(bag.bagId)
    }
}
