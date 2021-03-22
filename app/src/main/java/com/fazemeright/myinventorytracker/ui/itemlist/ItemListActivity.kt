package com.fazemeright.myinventorytracker.ui.itemlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.domain.models.ItemWithBag
import com.fazemeright.myinventorytracker.databinding.ActivityItemListBinding
import com.fazemeright.myinventorytracker.ui.addbag.AddBagActivity
import com.fazemeright.myinventorytracker.ui.additem.AddItemActivity
import com.fazemeright.myinventorytracker.ui.base.BaseActivity
import com.fazemeright.myinventorytracker.ui.itemdetail.ItemDetailActivity
import com.fazemeright.myinventorytracker.ui.login.LoginActivity
import com.fazemeright.myinventorytracker.ui.settings.SettingsActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ItemListActivity : BaseActivity<ActivityItemListBinding>() {

    val viewModel: ItemListViewModel by viewModels()

    private lateinit var searchView: SearchView

    @Inject
    lateinit var selectedItem: ItemWithBag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        setSupportActionBar(toolbar)

        binding.itemList.layoutManager = LinearLayoutManager(this)

        val adapter = ItemListAdapter(ItemListAdapter.ItemListener(
            clickListener = { item ->
                viewModel.onItemClicked(item)
            },
            deleteClickListener = { itemId ->
                showConfirmationDialog(itemId)
                viewModel.onDeleteItemClicked(itemId)
            }
        ))

        binding.itemList.adapter = adapter

        viewModel.items.observe(this, {
            it?.let {
                adapter.updateList(it)
            }
            Timber.i("received in onCreate: ${it.size}")
        })

        viewModel.deletedItem.observe(this, { deletedItem ->
            Snackbar.make(
                binding.root, // Parent view
                "Item deleted from database.", // Message to show
                Snackbar.LENGTH_LONG //
            ).setAction( // Set an action for snack bar
                "Undo" // Action button text
            ) {
                viewModel.undoDeleteItem(deletedItem)
            }.show()
        })

        viewModel.navigateToAddItemActivity.observe(this, { navigate ->
            if (navigate) {
                startActivity(Intent(this, AddItemActivity::class.java))
                viewModel.onNavigationToAddItemFinished()
            }
        })

        viewModel.navigateToItemDetailActivity.observe(this, { itemInBag ->
            itemInBag?.let {
                val intent = Intent(this, ItemDetailActivity::class.java)
                selectedItem.apply {
                    item = itemInBag.item
                    bag = itemInBag.bag
                }
                startActivity(intent)
                viewModel.onNavigationToItemDetailFinished()
            }
        })

        viewModel.userLoggedOut.observe(this, { userLoggedOut ->
            if (userLoggedOut) {
                open(LoginActivity::class.java)
                finish()
                viewModel.logoutSuccessful()
            }
        })
    }

    private fun showConfirmationDialog(itemId: Int) {
        AlertDialog.Builder(this)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu!!.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.queryHint = "Search Inventory for Items"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.onSearchClicked(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.onSearchClicked(query)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_bag -> startActivity(Intent(this, AddBagActivity::class.java))
            R.id.action_settings -> startActivity(Intent(this, SettingsActivity::class.java))
            R.id.action_logout -> viewModel.logoutClicked()
        }
        return true
    }

    override fun getLayoutId(): Int = R.layout.activity_item_list
}
