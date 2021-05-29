package com.fazemeright.myinventorytracker.ui.itemlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.databinding.FragmentItemListBinding
import com.fazemeright.myinventorytracker.domain.models.ItemWithBag
import com.fazemeright.myinventorytracker.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ItemListFragment : BaseFragment<FragmentItemListBinding>() {

    val viewModel: ItemListViewModel by viewModels()

    private lateinit var searchView: SearchView

    @Inject
    lateinit var selectedItem: ItemWithBag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

//        setSupportActionBar(toolbar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ItemListAdapter(
            ItemListAdapter.ItemListener(
                clickListener = { item ->
                    viewModel.onItemClicked(item)
                },
                deleteClickListener = { itemId ->
                    showConfirmationDialog(itemId)
                    viewModel.onDeleteItemClicked(itemId)
                }
            )
        )

        binding.itemList.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.items.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    adapter.updateList(it)
                }
                Timber.i("received in onCreate: ${it.size}")
            }
        )

        viewModel.deletedItem.observe(
            viewLifecycleOwner,
            { deletedItem ->
                Snackbar.make(
                    binding.root, // Parent view
                    "Item deleted from database.", // Message to show
                    Snackbar.LENGTH_LONG //
                ).setAction( // Set an action for snack bar
                    "Undo" // Action button text
                ) {
                    viewModel.undoDeleteItem(deletedItem)
                }.show()
            }
        )

        viewModel.navigateToAddItemActivity.observe(
            viewLifecycleOwner,
            { navigate ->
                if (navigate) {
//                    TODO: startActivity(Intent(this, AddItemFragment::class.java))
                    viewModel.onNavigationToAddItemFinished()
                }
            }
        )

        viewModel.navigateToItemDetailActivity.observe(
            viewLifecycleOwner,
            { itemInBag ->
                itemInBag?.let {
                    /* TODO: val intent = Intent(this, ItemDetailFragment::class.java)
                    selectedItem.apply {
                        item = itemInBag.item
                        bag = itemInBag.bag
                    }
                    startActivity(intent)*/
                    viewModel.onNavigationToItemDetailFinished()
                }
            }
        )

        viewModel.userLoggedOut.observe(viewLifecycleOwner, { userLoggedOut ->
            if (userLoggedOut) {
//                TODO: Use NavController
//                open(LoginFragment::class.java)
                findNavController().popBackStack()
                viewModel.logoutSuccessful()
            }
        })
    }

    private fun showConfirmationDialog(itemId: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Are you sure")
            .setMessage("Do you want to delete this entry?")
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes") { _, _ ->
                viewModel.onDeleteItemClicked(itemId)
            }
            // negative button text and action
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
        searchView = (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            isSubmitButtonEnabled = true
            queryHint = "Search Inventory for Items"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.onSearchClicked(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.onSearchClicked(query)
                    return true
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_bag -> {
//            TODO: startActivity(Intent(this, AddBagFragment::class.java))
            }
            R.id.action_settings -> {
                /* TODO: startActivity(
                    Intent(
                        this,
                        SettingsActivity::class.java
                    )
                )*/
            }
            R.id.action_logout -> viewModel.logoutClicked()
        }
        return true
    }

    override fun getViewBinding(): FragmentItemListBinding =
        FragmentItemListBinding.inflate(layoutInflater)
}
