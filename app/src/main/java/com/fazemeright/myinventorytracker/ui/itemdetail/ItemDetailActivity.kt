package com.fazemeright.myinventorytracker.ui.itemdetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.fazemeright.myinventorytracker.R
import com.fazemeright.myinventorytracker.database.inventoryitem.ItemWithBag
import com.fazemeright.myinventorytracker.databinding.ActivityItemDetailBinding
import com.fazemeright.myinventorytracker.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ItemDetailActivity : BaseActivity<ActivityItemDetailBinding>(),
    AdapterView.OnItemSelectedListener {

    val viewModel: ItemDetailViewModel by viewModels()

    @Inject
    lateinit var selectedBag: ItemWithBag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.item_detail_title)
        }

        viewModel.item.observe(this, Observer { item ->
            Timber.d("Detail Item: $item")
            item?.let {
                // Create an ArrayAdapter using a simple spinner layout and languages array
                val aa =
                    ArrayAdapter(this, android.R.layout.simple_spinner_item, listOf(it.bag.bagName))
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

    override fun getLayoutId(): Int = R.layout.activity_item_detail
}