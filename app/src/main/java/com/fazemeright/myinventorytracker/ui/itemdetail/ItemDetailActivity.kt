package com.fazemeright.myinventorytracker.ui.itemdetail

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.database.InventoryDatabase
import com.fazemeright.myinventorytracker.database.inventoryitem.InventoryItemDao
import com.fazemeright.myinventorytracker.databinding.ActivityItemDetailBinding

class ItemDetailActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var selectedBagName: String? = null

    val viewModel: ItemDetailViewModel by viewModels {
        ItemDetailViewModelFactory(
            application = application,
            dataSource = InventoryDatabase.getInstance(application),
            itemInBag = intent.getSerializableExtra("itemInBag") as InventoryItemDao.ItemInBag
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityItemDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_item_detail)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.item)
        }

        viewModel.item.observe(this, Observer { item ->
            Log.d("DebugData", "Detail Item: $item")
            item?.let {
                // Create an ArrayAdapter using a simple spinner layout and languages array
                val aa =
                    ArrayAdapter(this, android.R.layout.simple_spinner_item, listOf(it.bagName))
                // Set layout to use when the list of choices appear
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Set Adapter to Spinner
                binding.spinnerBag.adapter = aa

                binding.spinnerBag.onItemSelectedListener = this

                binding.item = it
            }
        })
        /*viewModel.bagNames.observe(this, Observer { bagNames ->
            // Create an ArrayAdapter using a simple spinner layout and languages array
            val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, bagNames)
            // Set layout to use when the list of choices appear
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set Adapter to Spinner
            binding.spinnerBag.adapter = aa

            binding.spinnerBag.onItemSelectedListener = this

        })*/

        viewModel.navigateBackToItemList.observe(this, Observer { navigate ->
            if (navigate) {
                finish()
                viewModel.onNavigationToAddItemFinished()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_update_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
//        TODO: Update the Bag description once user changes/updates the bag
//        selectedBagName = viewModel.bagNames.value?.get(position)
//        viewModel.updateBagDesc(selectedBagName)
    }
}